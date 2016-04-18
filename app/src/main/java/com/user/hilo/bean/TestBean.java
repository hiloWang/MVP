package com.user.hilo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/4/18.
 */
public class TestBean implements Serializable {

    /**
     * result : 200
     * liveStatus : 0
     * matchType2 : 分组赛
     * matchType1 : 亚冠杯
     * homeTeamInfo : {"id":207,"name":"大阪钢巴","url":"http://pic.13322.com/icons/teams/100/tm_logo_207.png","gas":0,"rc":0,"yc":0,"corner":0,"score":0,"halfScore":0,"danger":0,"shot":0,"aside":0,"trapping":0,"columnals":0,"foul":0,"freeHit":0,"lineOut":0,"offSide":0,"lineUp":[]}
     * matchInfo : {"startTime":"2016-04-19 18:00","weather":0,"city":"","venue":"","serverTime":1460962802379}
     * guestTeamInfo : {"id":499,"name":"水原三星","url":"http://pic.13322.com/icons/teams/100/tm_logo_499.png","gas":0,"rc":0,"yc":0,"corner":0,"score":0,"halfScore":0,"danger":0,"shot":0,"aside":0,"trapping":0,"columnals":0,"foul":0,"freeHit":0,"lineOut":0,"offSide":0,"lineUp":[]}
     */

    private String result;
    private String liveStatus;
    private String matchType2;
    private String matchType1;
    /**
     * id : 207
     * name : 大阪钢巴
     * url : http://pic.13322.com/icons/teams/100/tm_logo_207.png
     * gas : 0
     * rc : 0
     * yc : 0
     * corner : 0
     * score : 0
     * halfScore : 0
     * danger : 0
     * shot : 0
     * aside : 0
     * trapping : 0
     * columnals : 0
     * foul : 0
     * freeHit : 0
     * lineOut : 0
     * offSide : 0
     * lineUp : []
     */

    private HomeTeamInfoBean homeTeamInfo;
    /**
     * startTime : 2016-04-19 18:00
     * weather : 0
     * city :
     * venue :
     * serverTime : 1460962802379
     */

    private MatchInfoBean matchInfo;
    /**
     * id : 499
     * name : 水原三星
     * url : http://pic.13322.com/icons/teams/100/tm_logo_499.png
     * gas : 0
     * rc : 0
     * yc : 0
     * corner : 0
     * score : 0
     * halfScore : 0
     * danger : 0
     * shot : 0
     * aside : 0
     * trapping : 0
     * columnals : 0
     * foul : 0
     * freeHit : 0
     * lineOut : 0
     * offSide : 0
     * lineUp : []
     */

    private GuestTeamInfoBean guestTeamInfo;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(String liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getMatchType2() {
        return matchType2;
    }

    public void setMatchType2(String matchType2) {
        this.matchType2 = matchType2;
    }

    public String getMatchType1() {
        return matchType1;
    }

    public void setMatchType1(String matchType1) {
        this.matchType1 = matchType1;
    }

    public HomeTeamInfoBean getHomeTeamInfo() {
        return homeTeamInfo;
    }

    public void setHomeTeamInfo(HomeTeamInfoBean homeTeamInfo) {
        this.homeTeamInfo = homeTeamInfo;
    }

    public MatchInfoBean getMatchInfo() {
        return matchInfo;
    }

    public void setMatchInfo(MatchInfoBean matchInfo) {
        this.matchInfo = matchInfo;
    }

    public GuestTeamInfoBean getGuestTeamInfo() {
        return guestTeamInfo;
    }

    public void setGuestTeamInfo(GuestTeamInfoBean guestTeamInfo) {
        this.guestTeamInfo = guestTeamInfo;
    }

    public static class HomeTeamInfoBean {
        private int id;
        private String name;
        private String url;
        private int gas;
        private int rc;
        private int yc;
        private int corner;
        private int score;
        private int halfScore;
        private int danger;
        private int shot;
        private int aside;
        private int trapping;
        private int columnals;
        private int foul;
        private int freeHit;
        private int lineOut;
        private int offSide;
        private List<?> lineUp;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getGas() {
            return gas;
        }

        public void setGas(int gas) {
            this.gas = gas;
        }

        public int getRc() {
            return rc;
        }

        public void setRc(int rc) {
            this.rc = rc;
        }

        public int getYc() {
            return yc;
        }

        public void setYc(int yc) {
            this.yc = yc;
        }

        public int getCorner() {
            return corner;
        }

        public void setCorner(int corner) {
            this.corner = corner;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getHalfScore() {
            return halfScore;
        }

        public void setHalfScore(int halfScore) {
            this.halfScore = halfScore;
        }

        public int getDanger() {
            return danger;
        }

        public void setDanger(int danger) {
            this.danger = danger;
        }

        public int getShot() {
            return shot;
        }

        public void setShot(int shot) {
            this.shot = shot;
        }

        public int getAside() {
            return aside;
        }

        public void setAside(int aside) {
            this.aside = aside;
        }

        public int getTrapping() {
            return trapping;
        }

        public void setTrapping(int trapping) {
            this.trapping = trapping;
        }

        public int getColumnals() {
            return columnals;
        }

        public void setColumnals(int columnals) {
            this.columnals = columnals;
        }

        public int getFoul() {
            return foul;
        }

        public void setFoul(int foul) {
            this.foul = foul;
        }

        public int getFreeHit() {
            return freeHit;
        }

        public void setFreeHit(int freeHit) {
            this.freeHit = freeHit;
        }

        public int getLineOut() {
            return lineOut;
        }

        public void setLineOut(int lineOut) {
            this.lineOut = lineOut;
        }

        public int getOffSide() {
            return offSide;
        }

        public void setOffSide(int offSide) {
            this.offSide = offSide;
        }

        public List<?> getLineUp() {
            return lineUp;
        }

        public void setLineUp(List<?> lineUp) {
            this.lineUp = lineUp;
        }

        @Override
        public String toString() {
            return "HomeTeamInfoBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", url='" + url + '\'' +
                    ", gas=" + gas +
                    ", rc=" + rc +
                    ", yc=" + yc +
                    ", corner=" + corner +
                    ", score=" + score +
                    ", halfScore=" + halfScore +
                    ", danger=" + danger +
                    ", shot=" + shot +
                    ", aside=" + aside +
                    ", trapping=" + trapping +
                    ", columnals=" + columnals +
                    ", foul=" + foul +
                    ", freeHit=" + freeHit +
                    ", lineOut=" + lineOut +
                    ", offSide=" + offSide +
                    ", lineUp=" + lineUp +
                    '}';
        }
    }

    public static class MatchInfoBean {
        private String startTime;
        private int weather;
        private String city;
        private String venue;
        private long serverTime;

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public int getWeather() {
            return weather;
        }

        public void setWeather(int weather) {
            this.weather = weather;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getVenue() {
            return venue;
        }

        public void setVenue(String venue) {
            this.venue = venue;
        }

        public long getServerTime() {
            return serverTime;
        }

        public void setServerTime(long serverTime) {
            this.serverTime = serverTime;
        }

        @Override
        public String toString() {
            return "MatchInfoBean{" +
                    "startTime='" + startTime + '\'' +
                    ", weather=" + weather +
                    ", city='" + city + '\'' +
                    ", venue='" + venue + '\'' +
                    ", serverTime=" + serverTime +
                    '}';
        }
    }

    public static class GuestTeamInfoBean {
        private int id;
        private String name;
        private String url;
        private int gas;
        private int rc;
        private int yc;
        private int corner;
        private int score;
        private int halfScore;
        private int danger;
        private int shot;
        private int aside;
        private int trapping;
        private int columnals;
        private int foul;
        private int freeHit;
        private int lineOut;
        private int offSide;
        private List<?> lineUp;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getGas() {
            return gas;
        }

        public void setGas(int gas) {
            this.gas = gas;
        }

        public int getRc() {
            return rc;
        }

        public void setRc(int rc) {
            this.rc = rc;
        }

        public int getYc() {
            return yc;
        }

        public void setYc(int yc) {
            this.yc = yc;
        }

        public int getCorner() {
            return corner;
        }

        public void setCorner(int corner) {
            this.corner = corner;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getHalfScore() {
            return halfScore;
        }

        public void setHalfScore(int halfScore) {
            this.halfScore = halfScore;
        }

        public int getDanger() {
            return danger;
        }

        public void setDanger(int danger) {
            this.danger = danger;
        }

        public int getShot() {
            return shot;
        }

        public void setShot(int shot) {
            this.shot = shot;
        }

        public int getAside() {
            return aside;
        }

        public void setAside(int aside) {
            this.aside = aside;
        }

        public int getTrapping() {
            return trapping;
        }

        public void setTrapping(int trapping) {
            this.trapping = trapping;
        }

        public int getColumnals() {
            return columnals;
        }

        public void setColumnals(int columnals) {
            this.columnals = columnals;
        }

        public int getFoul() {
            return foul;
        }

        public void setFoul(int foul) {
            this.foul = foul;
        }

        public int getFreeHit() {
            return freeHit;
        }

        public void setFreeHit(int freeHit) {
            this.freeHit = freeHit;
        }

        public int getLineOut() {
            return lineOut;
        }

        public void setLineOut(int lineOut) {
            this.lineOut = lineOut;
        }

        public int getOffSide() {
            return offSide;
        }

        public void setOffSide(int offSide) {
            this.offSide = offSide;
        }

        public List<?> getLineUp() {
            return lineUp;
        }

        public void setLineUp(List<?> lineUp) {
            this.lineUp = lineUp;
        }

        @Override
        public String toString() {
            return "GuestTeamInfoBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", url='" + url + '\'' +
                    ", gas=" + gas +
                    ", rc=" + rc +
                    ", yc=" + yc +
                    ", corner=" + corner +
                    ", score=" + score +
                    ", halfScore=" + halfScore +
                    ", danger=" + danger +
                    ", shot=" + shot +
                    ", aside=" + aside +
                    ", trapping=" + trapping +
                    ", columnals=" + columnals +
                    ", foul=" + foul +
                    ", freeHit=" + freeHit +
                    ", lineOut=" + lineOut +
                    ", offSide=" + offSide +
                    ", lineUp=" + lineUp +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "result='" + result + '\'' +
                ", liveStatus='" + liveStatus + '\'' +
                ", matchType2='" + matchType2 + '\'' +
                ", matchType1='" + matchType1 + '\'' +
                ", homeTeamInfo=" + homeTeamInfo +
                ", matchInfo=" + matchInfo +
                ", guestTeamInfo=" + guestTeamInfo +
                '}';
    }
}
