package iuh.fit;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        // Tách 2 DB
        List<Todo> writeDB = new ArrayList<>();
        List<Todo> readDB = new ArrayList<>();

        // Handler
        TodoCommandHandler commandHandler = new TodoCommandHandler(writeDB);
        TodoQueryHandler queryHandler = new TodoQueryHandler(readDB);

        // ===== COMMAND =====
        commandHandler.handle(new AddTodoCommand("Học CQRS"));
        commandHandler.handle(new AddTodoCommand("Làm bài tập"));

        // ===== SYNC (giả lập event) =====
        SyncService.sync(writeDB, readDB);

        // ===== QUERY =====
        List<Todo> todos = queryHandler.handle(new GetTodosQuery());

        for (Todo t : todos) {
            System.out.println(t);
        }
    }

}