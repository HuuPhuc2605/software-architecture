package iuh.fit;

import java.util.List;

class TodoQueryHandler {
    private List<Todo> readDB;

    public TodoQueryHandler(List<Todo> readDB) {
        this.readDB = readDB;
    }

    public List<Todo> handle(GetTodosQuery query) {
        System.out.println("READ: Getting todos...");
        return readDB;
    }
}