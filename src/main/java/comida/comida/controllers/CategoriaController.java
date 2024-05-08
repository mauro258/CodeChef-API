package comida.comida.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import comida.comida.entities.CategoriaEntity;
import comida.comida.helpers.ApiResponse;
import comida.comida.repositories.CategoriaRepository;
import comida.comida.services.CategoriaService;
import jakarta.validation.Valid;


@RestController
@CrossOrigin(origins = "*")
public class CategoriaController {
        @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaService categoriaService;



    public Map<String, Object>  setApiResponse(ApiResponse data){
        Map<String, Object> response=new HashMap<>();
        response.put("ok", data.isOk());
        response.put("message", data.getMessage());
        response.put("data", data.getData());
        return response;

    }


    public ResponseEntity<?> validFields(BindingResult result){
        Map<String, Object> response=new HashMap<>();
        List<String> errors = result.getFieldErrors().stream().map(err->err.getDefaultMessage()).collect(Collectors.toList());
        response=setApiResponse(new ApiResponse<>(false, errors,""));
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/categorias")
    public ResponseEntity<?>listCategorias(){
        Map<String, Object> response=new HashMap<>();

        try {
            List<CategoriaEntity> categorias=categoriaRepository.findAll();
            response= setApiResponse(new ApiResponse<>(true, "lista de categoria" , categorias));
            return new ResponseEntity<>(response,HttpStatus.OK);

        }catch (DataAccessException e) {

            response= setApiResponse(new ApiResponse<>(false, "error al obtener la lista de categorias" + e.getMessage() + e.getMostSpecificCause().getMessage(), ""));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

//metodo para obtner un registro por el id
    @GetMapping("/categorias/{id}")
    public ResponseEntity<?> getCategoriaByid(@PathVariable Long id){
        Map<String, Object> response=new HashMap<>();
        try {


            CategoriaEntity categoria=categoriaRepository.findById(id).orElse(null);
            if (categoria==null){
                response=setApiResponse(new ApiResponse<>(false, "categoria no encontrado", ""));
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response=setApiResponse(new ApiResponse<>(true, "categoria encontrado", categoria));
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (DataAccessException e) {
            response= setApiResponse(new ApiResponse<>(false, "error al obtener la categoria por el id" + e.getMessage() + e.getMostSpecificCause().getMessage(), ""));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/categorias")
    public ResponseEntity<?> saveCategoria(@Valid @RequestBody CategoriaEntity categoria,BindingResult result){
        Map<String, Object> response=new HashMap<>();
        if (result.hasErrors()){
            return validFields(result);

        }
        try {
            // categoriaRepository.save(categoria);
            categoriaService.saveCategoria(categoria);
            response=setApiResponse(new ApiResponse<>(true, "categoria guardado", categoria));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            response= setApiResponse(new ApiResponse<>(false, "error al guardar las categorias" + e.getMessage() + e.getMostSpecificCause().getMessage(), ""));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @PutMapping("/categorias/{id}")
        public ResponseEntity<?> updateCategoria(@Valid @RequestBody CategoriaEntity categoria, BindingResult result, @PathVariable long id){

            Map<String, Object> response=new HashMap<>();
            if (result.hasErrors()){
                return validFields(result);

            }
            try {
                CategoriaEntity findCategoria=categoriaRepository.findById(id).orElse(null);
                if (findCategoria==null){
                    response=setApiResponse(new ApiResponse<>(false, "categoria no encontrado", ""));
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
                
                findCategoria.setTitle(categoria.getTitle());

    
    
                categoriaRepository.save(findCategoria);
                response=setApiResponse(new ApiResponse<>(true, "categoria actualizado", ""));
                return new ResponseEntity<>(response, HttpStatus.OK);
    
            } catch  (DataAccessException e) {
                response= setApiResponse(new ApiResponse<>(false, "error al actualizar la categoria" + e.getMessage() + e.getMostSpecificCause().getMessage(), ""));
                return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
            }


        }


    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable Long id){
        Map<String, Object> response=new HashMap<>();
        try {
            CategoriaEntity categoria=categoriaRepository.findById(id).orElse(null);
            if (categoria==null){
                response=setApiResponse(new ApiResponse<>(false, "categoria no encontrado", ""));
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            categoriaRepository.delete(categoria);
            response=setApiResponse(new ApiResponse<>(true, "categoria eliminado", ""));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response= setApiResponse(new ApiResponse<>(false, "error al eliminar las categorias por el id" + e.getMessage() + e.getMostSpecificCause().getMessage(), ""));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    }
















