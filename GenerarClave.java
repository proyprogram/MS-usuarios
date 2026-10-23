import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerarClave {
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("Prueba123"));
    }
}
