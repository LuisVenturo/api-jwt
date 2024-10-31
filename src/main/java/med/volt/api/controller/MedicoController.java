package med.volt.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.volt.api.domain.medico.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;
    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriBuilder){
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico =  new DatosRespuestaMedico(medico.getId(), medico.getNombre(),medico.getEmail(),medico.getTelefono(),
                medico.getDocumento());
        URI url = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
    }

    /*@GetMapping
    public List<Medico> listarMedicos(){
        return medicoRepository.findAll();
    }*/
    /*@GetMapping
    public List<DatosListaMedico> listarMedicos(){
        return medicoRepository.findAll().stream().map(DatosListaMedico::new).toList();
    }*/
    @GetMapping
    public ResponseEntity<Page<DatosListaMedico>>  listarMedicos(Pageable paginacion){
        return ResponseEntity.ok(medicoRepository.findAll(paginacion).map(DatosListaMedico::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizaMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getDocumento(), medico.getEmail(),
                medico.getTelefono()));

    }

    // DELETE LOGICO
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    //    DELETE EN BASE DE DATOD
//    public void eliminarMedico(@PathVariable Long id) {
//        Medico medico = medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);
//    }

    @GetMapping("/{id}")
    public ResponseEntity retornaDatosMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getDocumento(), medico.getEmail(),
                medico.getTelefono());
        return ResponseEntity.ok(datosRespuestaMedico);
    }
}
