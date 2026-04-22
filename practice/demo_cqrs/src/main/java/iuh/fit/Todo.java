package iuh.fit;

class Todo {
    public int id;
    public String title;

    public Todo(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public String toString() {
        return id + " - " + title;
    }
}