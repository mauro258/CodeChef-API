
package comida.comida.helpers;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T,K> {
    private boolean ok;
    private K message;
    private T data;
}
