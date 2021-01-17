package pl.teamsix.competenceproject.logic.generation;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.springframework.stereotype.Service;
import pl.teamsix.competenceproject.domain.entity.Hotspot;
import pl.teamsix.competenceproject.domain.entity.Trace;
import pl.teamsix.competenceproject.domain.entity.User;
import pl.teamsix.competenceproject.domain.service.trace.TraceService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class TracesGenerator {

    private static final int BATCH_SIZE = 100;
    private static final int MILLISECONDS_IN_HOUR = 3600000;

    private final List<Trace> traces = new ArrayList<>();

    private final TraceService traceService;

    public TracesGenerator(final TraceService traceService) {
        this.traceService = traceService;
    }

    public void generate(List<User> users, List<Hotspot> hotspots, Date startTime,
                         double durationInHours, double avgMovementsPerHour) {
        long duration = (long) (durationInHours * MILLISECONDS_IN_HOUR);
        double lambda = avgMovementsPerHour / MILLISECONDS_IN_HOUR;
        final ExponentialDistribution exitDistribution = new ExponentialDistribution(1.0 / lambda);
        final ExponentialDistribution[] entryDistributions = new ExponentialDistribution[24];
        for (int i = 0; i < 24; i++) {
            entryDistributions[i] =
                    new ExponentialDistribution(1.0 / (lambda * Math.exp(-(Math.pow(i - 12, 2) / 40.0))));
        }
        final ExponentialDistribution hotspotsDistribution = new ExponentialDistribution(1.0 / 6.0);
        for (User user : users) {
            double time = startTime.getTime();
            while (time < startTime.getTime() + duration) {
                double entryTime = time
                        + entryDistributions[(int) (((long) time) / MILLISECONDS_IN_HOUR % 24)].sample();
                double exitTime = entryTime + exitDistribution.sample();
                int nextHotspot;
                do {
                    nextHotspot = (int) (hotspotsDistribution.sample() * hotspots.size());
                } while (nextHotspot >= hotspots.size());
                generateSingleTrace(user, hotspots.get(nextHotspot), new Date((long) entryTime),
                        new Date((long) exitTime));
                time = exitTime;
            }
        }
        traceService.saveAll(traces);
        traces.clear();
    }

    private void generateSingleTrace(User user, Hotspot hotspot, Date entryTime, Date exitTime) {
        traces.add(new Trace(user, hotspot, entryTime, exitTime));
        if (traces.size() == BATCH_SIZE) {
            traceService.saveAll(traces);
            traces.clear();
        }
    }
}
