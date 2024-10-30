package med.volt.api.domain.consulta.validaciones;

import med.volt.api.domain.ValidacionException;
import med.volt.api.domain.consulta.DatosReservaConsulta;
import med.volt.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoActivo implements ValidadorDeConsultas {
    @Autowired
    private MedicoRepository repository;

    public void validar(DatosReservaConsulta datos){
        //eleccion del medico opcional
        if (datos.idMedico() == null){
            return;
        }
        var medicoEstaActivo = repository.findActiviById(datos.idMedico());
        if (!medicoEstaActivo){
            throw new ValidacionException("Consulta no puede ser reservada con medico excluido");
        }
    }
}
