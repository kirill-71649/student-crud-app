package pl.vistula.student.crud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StudentCrudAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentCrudAppApplication.class, args);
    }

    @Bean
    CommandLineRunner loadSampleBooks(BookRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Book("Clean Code", "Robert C. Martin", 2008));
                repository.save(new Book("The Pragmatic Programmer", "Andrew Hunt", 1999));
                repository.save(new Book("Effective Java", "Joshua Bloch", 2018));
            }
        };
    }
}
