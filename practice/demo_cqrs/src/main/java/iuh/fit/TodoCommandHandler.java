package iuh.fit;

import java.util.List;

class TodoCommandHandler {
    private List<Todo> writeDB;

    public TodoCommandHandler(List<Todo> writeDB) {
        this.writeDB = writeDB;
    }

    public void handle(AddTodoCommand command) {
        int id = writeDB.size() + 1;
        Todo todo = new Todo(id, command.title);

        writeDB.add(todo);
        System.out.println("WRITE: Adding todo...");

    }
}
