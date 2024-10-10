package com.boostmyfool.beastore.controllers;

import com.boostmyfool.beastore.models.Productos;
import com.boostmyfool.beastore.models.ProductosDTO;
import com.boostmyfool.beastore.repositories.ProductosRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/productos")
public class ControladorProductos {
    @Autowired
    private ProductosRepository repo;

    @GetMapping({"","/"})
    public String mostrarListaProductos(Model model){
        List<Productos> productos = repo.findAll(Sort.by(Sort.Direction.DESC,"id"));
        model.addAttribute("productos",productos);
        return "productos/index";
    }

    @GetMapping("/crear")
    public String mostrarCreacionProductos(Model model){
        ProductosDTO productoDTO = new ProductosDTO();
        model.addAttribute("productosDTO",productoDTO);
        return "productos/crearProducto";
    }
    @PostMapping("/crear")
    public String crearProducto(@Valid @ModelAttribute ProductosDTO productoDTO, BindingResult resultado) throws IOException {
        if (productoDTO.getImagenArchivo().isEmpty()){
            resultado.addError(new FieldError("productoDTO","imagenArchivo","El archivo de imagen es necesario. . ."));
        }
        if (resultado.hasErrors()){
            return "productos/crearProducto";
        }
        MultipartFile imagen = productoDTO.getImagenArchivo();
        Date creadoEn = new Date();
        String nombreGuardar = creadoEn.getTime()+ "_" + imagen.getOriginalFilename();
        try{
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            try(InputStream inputStream = imagen.getInputStream()){
                Files.copy(inputStream, Paths.get(uploadDir+nombreGuardar), StandardCopyOption.REPLACE_EXISTING);
            }
        }catch (Exception e){
            System.out.println("Excepcion: "+e.getMessage());
        }
        Productos producto = new Productos();
        producto.setNombre(productoDTO.getNombre());
        producto.setMarca(productoDTO.getMarca());
        producto.setCategoria(productoDTO.getCategoria());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setFechaCreado(creadoEn);
        producto.setImagenArchivo(nombreGuardar);
        repo.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/edit/{id}")
    public String mostrarModificacionProductos(Model model, @PathVariable int id) {
        try {
            Productos producto = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
            model.addAttribute("producto", producto);

            ProductosDTO productoDTO = new ProductosDTO();
            productoDTO.setNombre(producto.getNombre());
            productoDTO.setMarca(producto.getMarca());
            productoDTO.setCategoria(producto.getCategoria());
            productoDTO.setPrecio(producto.getPrecio());
            productoDTO.setDescripcion(producto.getDescripcion());
            model.addAttribute("productoDTO", productoDTO);
        } catch (Exception e) {
            System.out.println("Excepción: " + e.getMessage());
            return "redirect:/productos";
        }
        return "productos/editarProducto";  // Asegúrate que esta vista existe
    }

    @PostMapping("/edit/{id}")
    public String actualizarProducto(Model model, @PathVariable int id, @Valid @ModelAttribute ProductosDTO productoDTO,BindingResult result){
        try{
            Productos producto = repo.findById(id).get();
            model.addAttribute("producto",producto);
            if(result.hasErrors()){
                return "productos/editarProducto";
            }
            if(!productoDTO.getImagenArchivo().isEmpty()){
                String uploadDir = "public/images/";
                Path oldPath = Paths.get(uploadDir+producto.getImagenArchivo());
                try{
                    Files.delete(oldPath);
                }catch (Exception e){
                    System.out.println("Error "+e.getMessage());
                }
                MultipartFile imagen = productoDTO.getImagenArchivo();
                Date creadoEn = new Date();
                String nombreGuardar = creadoEn.getTime() + "_" + imagen.getOriginalFilename();
                try(InputStream inputStream = imagen.getInputStream()){
                    Files.copy(inputStream, Paths.get(uploadDir+nombreGuardar), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                producto.setImagenArchivo(nombreGuardar);
            }
            producto.setNombre(productoDTO.getNombre());
            producto.setMarca(productoDTO.getMarca());
            producto.setCategoria(productoDTO.getCategoria());
            producto.setPrecio(productoDTO.getPrecio());
            producto.setDescripcion(productoDTO.getDescripcion());
            repo.save(producto);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/productos";
    }
    @PostMapping("/delete/{id}")
    public String eliminarProducto(@PathVariable int id) {
        try {
            Productos producto = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
            Path oldPath = Paths.get("public/images/" + producto.getImagenArchivo());
            try {
                Files.deleteIfExists(oldPath);
            } catch (Exception e) {
                System.out.println("Error al eliminar la imagen: " + e.getMessage());
            }
            repo.delete(producto);
        } catch (Exception e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
        }
        return "redirect:/productos";
    }

}
