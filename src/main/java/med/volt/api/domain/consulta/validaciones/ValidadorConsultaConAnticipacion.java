package med.volt.api.domain.consulta.validaciones;

import med.volt.api.domain.ValidacionException;
import med.volt.api.domain.consulta.DatosReservaConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
@Component //cuando tenemos un componente generico cuando necesitamos que spring lo cargue de forma generica
public class ValidadorConsultaConAnticipacion implements ValidadorDeConsultas {

    public void validar(DatosReservaConsulta datos){
        var fechaConsulta = datos.fecha();
        var ahora = LocalDateTime.now();
        var diferenciaEnMinutos = Duration.between(ahora, fechaConsulta).toMinutes();
        if (diferenciaEnMinutos < 30 ){
            throw new ValidacionException("Horario Seleccionado con menos de 30 minutos de anticipación");
        }
    }
}
