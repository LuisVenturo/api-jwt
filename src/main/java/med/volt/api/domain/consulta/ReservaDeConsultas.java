package med.volt.api.domain.consulta;

import med.volt.api.domain.ValidacionException;
import med.volt.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.volt.api.domain.medico.Medico;
import med.volt.api.domain.medico.MedicoRepository;
import med.volt.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaDeConsultas {
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private List<ValidadorDeConsultas> validadores;

    public void reservar(DatosReservaConsulta datos){

        if (!pacienteRepository.existsById(datos.idPaciente())){
            throw new ValidacionException("No existe un paciente con el id informado");
        }
        if (datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionException("No existe un medico con el id informado");
        }

        //validaciones
        validadores.forEach(v -> v.validar(datos));

        var medico = elegirMedico(datos);
        var paciente = pacienteRepository.findById(datos.idPaciente()).get();

        var consulta = new Consulta(null, medico, paciente, datos.fecha(), null);
        consultaRepository.save(consulta);
    }

    private Medico elegirMedico(DatosReservaConsulta datos) {
        if (datos.idMedico() != null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if (datos.especialidad() == null){
            throw new ValidacionException("Es necesario elegir una especialidad cuando se elige un medico");
        }

        return medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(datos.especialidad(), datos.fecha());
    }

    public void cancelar(DatosCancelamientoConsulta datos){
        if (!consultaRepository.existsById(datos.idConsulta())){
            throw new ValidacionException("Id de la consulta informado no existe!");
        }
        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }
}
