package iuh.fit;

import java.util.List;

class SyncService {
    public static void sync(List<Todo> writeDB, List<Todo> readDB) {
        readDB.clear();
        readDB.addAll(writeDB); // copy dữ liệu
        System.out.println("SYNC: WriteDB → ReadDB");
    }
}
