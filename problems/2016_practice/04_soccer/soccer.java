import java.io.*;
import java.util.*;

public class soccer
{
    public static void main(String[] args) throws Exception
    {
        new soccer();
    }

    boolean FILE_INPUT = true;
    public soccer() throws Exception
    {
        Scanner sc = new Scanner(System.in);
        if(FILE_INPUT)
            sc = new Scanner(new File("soccer.in"));

        int T = sc.nextInt();
        for(int t = 1; t <= T; t++)
        {
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            int N = sc.nextInt(), G = sc.nextInt();
            Team[] teams = new Team[N];
            for(int i = 0; i < N; i++)
            {
                String name = sc.next();
                map.put(name, i);
                teams[i] = new Team(name);
            }

            while(G-- > 0)
            {
                int a = map.get(sc.next()), ascore = sc.nextInt();
                int b = map.get(sc.next()), bscore = sc.nextInt();

                update(teams, a, ascore, b, bscore);
            }

            for(int i = 0; i < N; i++)
                teams[i].points = teams[i].wins*3 + teams[i].draws;
            Arrays.sort(teams);


            System.out.printf("Group %d:\n", t);
            for(Team team : teams)
                System.out.println(team);
            System.out.println();
        }
    }

    void update(Team[] teams, int a, int ascore, int b, int bscore)
    {
        if(ascore < bscore)
        {
            update(teams, b, bscore, a, ascore);
            return;
        }
        else if(ascore == bscore)
        {
            teams[a].draws++;
            teams[b].draws++;
        }
        else
        {
            teams[a].wins++;
            teams[b].losses++;
        }
        teams[a].scored += ascore;
        teams[a].allowed += bscore;
        teams[b].scored += bscore;
        teams[b].allowed += ascore;
    }

    class Team implements Comparable<Team>
    {
        String name;
        int points, wins, losses, draws, scored, allowed;

        public Team(String name) { this.name = name; }

        public int compareTo(Team other)
        {
            if(points != other.points) return other.points-points;
            if(scored-allowed != other.scored-other.allowed) return (other.scored-other.allowed)-(scored-allowed);
            if(scored != other.scored) return other.scored-scored;
            return name.compareTo(other.name);
        }

        public String toString()
        {
            return String.format("%s %d %d %d %d %d %d", name, points, wins, losses, draws, scored, allowed);
        }
    }
}

