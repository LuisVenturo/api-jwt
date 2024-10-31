package med.volt.api.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import med.volt.api.domain.ValidacionException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404(){
        return ResponseEntity.notFound().build();
    }

    /*  @ExceptionHandler(EntityNotFoundException.class)
      public ResponseEntity manejarError404() {
          return ResponseEntity.notFound().build();
      }

      @ExceptionHandler(MethodArgumentNotValidException.class)
      public ResponseEntity manejarErro400(MethodArgumentNotValidException ex) {
          var errores = ex.getFieldErrors();
          return ResponseEntity.badRequest().body(errores.stream().map(DatosErrorValidacion::new).toList());
      }

      private record DatosErrorValidacion(String campo, String mensaje) {
          public DatosErrorValidacion(FieldError error) {
              this(error.getField(), error.getDefaultMessage());
          }
      }*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e){
        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity tratarErorDevalidacion(ValidacionException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    private record DatosErrorValidacion(String campo, String error){
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
