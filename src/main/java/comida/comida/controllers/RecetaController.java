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

import comida.comida.entities.RecetaEntity;
import comida.comida.helpers.ApiResponse;
import comida.comida.repositories.RecetaRepository;
import comida.comida.services.RecetaService;
import jakarta.validation.Valid;




@RestController
@CrossOrigin(origins = "*")
public class RecetaController {
        @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private RecetaService recetaService;



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

    @GetMapping("/recetas")
    public ResponseEntity<?>listRecetas(){
        Map<String, Object> response=new HashMap<>();

        try {
            List<RecetaEntity> recetas=recetaRepository.findAll();
            response= setApiResponse(new ApiResponse<>(true, "lista de receta" , recetas));
            return new ResponseEntity<>(response,HttpStatus.OK);

        }catch (DataAccessException e) {

            response= setApiResponse(new ApiResponse<>(false, "error al obtener la lista de recetas" + e.getMessage() + e.getMostSpecificCause().getMessage(), ""));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

//metodo para obtner un registro por el id
    @GetMapping("/recetas/{id}")
    public ResponseEntity<?> getRecetaByid(@PathVariable Long id){
        Map<String, Object> response=new HashMap<>();
        try {


            RecetaEntity receta=recetaRepository.findById(id).orElse(null);
            if (receta==null){
                response=setApiResponse(new ApiResponse<>(false, "receta no encontrado", ""));
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response=setApiResponse(new ApiResponse<>(true, "receta encontrado", receta));
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (DataAccessException e) {
            response= setApiResponse(new ApiResponse<>(false, "error al obtener las recetas por el id" + e.getMessage() + e.getMostSpecificCause().getMessage(), ""));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/recetas")
    public ResponseEntity<?> saveReceta(@Valid @RequestBody RecetaEntity receta,BindingResult result){
        Map<String, Object> response=new HashMap<>();
        if (result.hasErrors()){
            return validFields(result);

        }
        try {
            // recetaRepository.save(receta);
            recetaService.saveReceta(receta);
            response=setApiResponse(new ApiResponse<>(true, "receta guardado", receta));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            response= setApiResponse(new ApiResponse<>(false, "error al guardar unas recetas" + e.getMessage() + e.getMostSpecificCause().getMessage(), ""));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @PutMapping("/recetas/{id}")
        public ResponseEntity<?> updateReceta(@Valid @RequestBody RecetaEntity receta, BindingResult result, @PathVariable long id){

            Map<String, Object> response=new HashMap<>();
            if (result.hasErrors()){
                return validFields(result);

            }
            try {
                RecetaEntity findReceta=recetaRepository.findById(id).orElse(null);
                if (findReceta==null){
                    response=setApiResponse(new ApiResponse<>(false, "receta no encontrado", ""));
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
                
                findReceta.setTitle(receta.getTitle());
                findReceta.setDescripcion(receta.getDescripcion());
                findReceta.setIngredientes(receta.getIngredientes());
                findReceta.setImgUrl(receta.getImgUrl());
                findReceta.setVidUrl(receta.getVidUrl());

    
    
                recetaRepository.save(findReceta);
                response=setApiResponse(new ApiResponse<>(true, "receta actualizado", ""));
                return new ResponseEntity<>(response, HttpStatus.OK);
    
            } catch  (DataAccessException e) {
                response= setApiResponse(new ApiResponse<>(false, "error al actualizar el receta" + e.getMessage() + e.getMostSpecificCause().getMessage(), ""));
                return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
            }


        }


    @DeleteMapping("/recetas/{id}")
    public ResponseEntity<?> deleteReceta(@PathVariable Long id){
        Map<String, Object> response=new HashMap<>();
        try {
            RecetaEntity receta=recetaRepository.findById(id).orElse(null);
            if (receta==null){
                response=setApiResponse(new ApiResponse<>(false, "receta no encontrado", ""));
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            recetaRepository.delete(receta);
            response=setApiResponse(new ApiResponse<>(true, "receta eliminado", ""));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response= setApiResponse(new ApiResponse<>(false, "error al eliminar el recetas por el id" + e.getMessage() + e.getMostSpecificCause().getMessage(), ""));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    }

