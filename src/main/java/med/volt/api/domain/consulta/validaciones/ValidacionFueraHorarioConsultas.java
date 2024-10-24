package med.volt.api.domain.consulta.validaciones;

import med.volt.api.domain.ValidacionException;
import med.volt.api.domain.consulta.DatosReservaConsulta;

import java.time.DayOfWeek;

public class ValidacionFueraHorarioConsultas {

    public void validar(DatosReservaConsulta datos){
        var fechaConsulta = datos.fecha();
        var domingo = fechaConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horarioAnteaDeAperturaClinica = fechaConsulta.getHour() < 7;
        var horarioDespuesDeCierreClinica = fechaConsulta.getHour() > 18;

        if (domingo || horarioAnteaDeAperturaClinica || horarioDespuesDeCierreClinica ){
            throw  new ValidacionException("Horario selecionado fuera del horario de atendimiento de la clinica");
        }
    }
}
