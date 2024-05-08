package comida.comida.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recetas")
public class RecetaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @NotNull(message = "El campo title es obligatorio")
    @Size(min = 4, max = 50)
    @Column(name = "title")
    private String title;

    @NotNull(message = "El campo descripcion es obligatorio")

    @Column(name = "descripcion")
    private String descripcion;

    @NotNull(message = "El campo ingredientes es obligatorio")

    @Column(name = "ingredientes")
    private String ingredientes;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "vid_url")
    private String vidUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
    private CategoriaEntity categoria;

}
