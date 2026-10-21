package pl.vistula.student.crud;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    private final BookRepository repository;
    private final Grid<Book> grid = new Grid<>(Book.class, false);
    private final TextField title = new TextField("Title");
    private final TextField author = new TextField("Author");
    private final IntegerField publicationYear = new IntegerField("Year");
    private final TextField filter = new TextField("Find a book");
    private Book selectedBook;

    public MainView(BookRepository repository) {
        this.repository = repository;
        setSizeFull();
        setPadding(true);
        setMaxWidth("1100px");
        getStyle().set("margin", "0 auto");

        add(new H2("Book Catalogue"));
        add(new Paragraph("A small CRUD application for practising Java, Git and GitHub."));

        grid.addColumn(Book::getTitle).setHeader("Title").setAutoWidth(true).setFlexGrow(2);
        grid.addColumn(Book::getAuthor).setHeader("Author").setAutoWidth(true).setFlexGrow(2);
        grid.addColumn(Book::getPublicationYear).setHeader("Year").setAutoWidth(true);
        grid.setWidthFull();
        grid.setHeight("360px");
        grid.asSingleSelect().addValueChangeListener(event -> editBook(event.getValue()));

        filter.setPlaceholder("Search by title or author");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(event -> refreshGrid());

        title.setRequiredIndicatorVisible(true);
        author.setRequiredIndicatorVisible(true);
        publicationYear.setMin(1400);
        publicationYear.setMax(2100);
        publicationYear.setStepButtonsVisible(true);
        title.setWidthFull();
        author.setWidthFull();
        publicationYear.setWidth("150px");

        Button save = new Button("Save book", event -> saveBook());
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button clear = new Button("New book", event -> clearForm());
        Button delete = new Button("Delete selected", event -> deleteBook());
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        HorizontalLayout form = new HorizontalLayout(title, author, publicationYear);
        form.setWidthFull();
        form.setAlignItems(Alignment.END);
        HorizontalLayout actions = new HorizontalLayout(save, clear, delete);

        add(filter, grid, form, actions);
        refreshGrid();
    }

    private void refreshGrid() {
        String query = filter.getValue() == null ? "" : filter.getValue().trim().toLowerCase();
        grid.setItems(repository.findAll().stream()
                .filter(book -> query.isEmpty()
                        || book.getTitle().toLowerCase().contains(query)
                        || book.getAuthor().toLowerCase().contains(query))
                .toList());
    }

    private void editBook(Book book) {
        selectedBook = book;
        if (book == null) {
            return;
        }
        title.setValue(book.getTitle());
        author.setValue(book.getAuthor());
        publicationYear.setValue(book.getPublicationYear());
    }

    private void saveBook() {
        if (title.getValue().isBlank() || author.getValue().isBlank() || publicationYear.getValue() == null) {
            Notification.show("Please enter a title, author and year.");
            return;
        }
        if (publicationYear.getValue() < 1400 || publicationYear.getValue() > 2100) {
            Notification.show("Year must be between 1400 and 2100.");
            return;
        }

        Book book = selectedBook == null ? new Book("", "", 0) : selectedBook;
        book.setTitle(title.getValue().trim());
        book.setAuthor(author.getValue().trim());
        book.setPublicationYear(publicationYear.getValue());
        repository.save(book);
        clearForm();
        refreshGrid();
        Notification.show("Book saved.");
    }

    private void deleteBook() {
        if (selectedBook == null) {
            Notification.show("Select a book first.");
            return;
        }
        repository.delete(selectedBook);
        clearForm();
        refreshGrid();
        Notification.show("Book deleted.");
    }

    private void clearForm() {
        selectedBook = null;
        grid.asSingleSelect().clear();
        title.clear();
        author.clear();
        publicationYear.clear();
    }
}
