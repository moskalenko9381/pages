package task.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServiceImpl implements ServiceInterface {
    private Map<Boolean, List<String>> mapsYesterday;
    private Map<String, String> yesterday;
    private Map<String, String> today;

    public void process() {
        today = MapMaker.makeMapFromPath(Constant.TODAY_DIRECTORY);
        yesterday = MapMaker.makeMapFromPath(Constant.YESTERDAY_DIRECTORY);
        //  false - были только вчера
        //  true - были вчера, остались сегодня
        mapsYesterday = yesterday.keySet().stream()
                .collect(Collectors.partitioningBy(today::containsKey));

        ResourceIO.writeToFile(getLostPages(), getNewPages(), getChangedPages());
    }

    private String getChangedPages() {
        return mapsYesterday.get(true).stream()
                .filter(x -> !today.get(x).equals(yesterday.get(x)))
                .collect(Collectors.joining("\n"));
    }

    private String getNewPages() {
        return today.keySet().stream()
                .filter(x -> !yesterday.containsKey(x))
                .collect(Collectors.joining("\n"));
    }

    private String getLostPages() {
        return String.join("\n", mapsYesterday.get(false));
    }
}
