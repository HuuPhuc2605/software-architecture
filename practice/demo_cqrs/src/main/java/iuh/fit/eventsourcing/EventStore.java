package iuh.fit.eventsourcing;

import java.util.*;

class EventStore {
    private List<Event> events = new ArrayList<>();

    public void save(Event event) {
        events.add(event);
    }

    public List<Event> getAllEvents() {
        return events;
    }
}