package med.volt.api.domain.medico;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.volt.api.domain.direccion.DatosDireccion;

public record DatosRegistroMedico(
        @NotBlank(message = "Nombre es obligatorio")
        String nombre,

        @NotBlank(message = "Email es obligatorio")
        @Email(message = "Formato de email es inválido")
        String email,

        @NotBlank(message = "Teléfono es obligatorio")
        String telefono,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String documento,
        @NotNull(message = "Especialidad es obligatorio")
        Especialidad especialidad,

        @NotNull(message = "Datos de dirección son obligatorios")
        @Valid DatosDireccion direccion
) {
}
