package secure.coding.chapter01.ids.ids00.solution;

import java.sql.SQLException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

//	@Autowired
//	MyUserRepository myUserRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) {
		
		try {
			new CreateUser().create("akatkar", "ak1234");
			
			Login login = new Login();
			// login.showTable();
			login.doPrivilegedAction("akatkar", "ak1234".toCharArray());
			login.doPrivilegedAction("sdshdgs' or 1=1;--", "yeyeyeye".toCharArray());
			login.doPrivilegedAction("sdshdgs", "yeyeyeye".toCharArray());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			;
		}
	}

}
