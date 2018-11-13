package testhelpers;

import com.lancefallon.usermgmt.player.model.College;
import com.lancefallon.usermgmt.player.model.Conference;
import com.lancefallon.usermgmt.player.model.Player;
import com.lancefallon.usermgmt.player.model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DomainFactory {

    public static College generateCollege(Integer id, String name) {
        Conference conference = generateConference(1, "TestConference");
        return new College(id, name, conference);
    }

    public static Conference generateConference(Integer id, String name) {
        return new Conference(id, name);
    }

    public static List<Player> createPlayers(int count) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Player player = new Player(i+1, i+1, "johndoe", generateCollege(1, "CollegeText"), "TestCollege", new Position(), 72.0, 200.0, 1, "1st", "2018", 2018);
            players.add(player);
        }
        return players;
    }
}
