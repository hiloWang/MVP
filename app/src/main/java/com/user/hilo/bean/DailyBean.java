package com.user.hilo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/3/24.
 */
public class DailyBean implements Serializable {

    /**
     * result : 200
     * menus : {"content":[{"jumpType":2,"jumpAddr":"13","title":"足球比分","picUrl":"http://m.1332255.com/oms/upload/shortcut/e2e788590d2146fdbfbf439981833d8e.png"},{"jumpType":2,"jumpAddr":"20","title":"篮球比分","picUrl":"http://m.1332255.com/oms/upload/shortcut/23a6e97e244a441b8615aefdf8948700.png"},{"jumpType":2,"jumpAddr":"11","title":"足球数据","picUrl":"http://m.1332255.com/oms/upload/shortcut/e2688aa33fc648c9a6ab964b0fdfc1c9.png"},{"jumpType":2,"jumpAddr":"14","title":"足球视频","picUrl":"http://m.1332255.com/oms/upload/shortcut/2bb91411f14947659afc2c4b0f955698.png"},{"jumpType":2,"jumpAddr":"30","title":"彩票开奖","picUrl":"http://m.1332255.com/oms/upload/shortcut/d9f5da682f584eb09868d251548b0a86.png"},{"jumpType":2,"jumpAddr":"31","title":"香港开奖","picUrl":"http://m.1332255.com/oms/upload/shortcut/3a89651e3a6c4967826f6d21f8865a75.png"}],"result":200}
     * otherLists : [{"content":{"labType":1,"bodys":[{"jumpType":2,"jumpAddr":"13","thirdId":"315148","racename":"阿根廷杯","raceId":"1183","raceColor":"#5288f4","date":"2016-04-19","time":"02:30","homeId":20758,"hometeam":"迪罗联邦","guestId":12436,"guestteam":"贝尔诺防卫","statusOrigin":"-1","homeScore":1,"guestScore":4,"homeHalfScore":0,"guestHalfScore":3,"homeLogoUrl":"http://pic.13322.com/icons/teams/100/20758.png","guestLogoUrl":"http://pic.13322.com/icons/teams/100/12436.png"},{"jumpType":2,"jumpAddr":"13","thirdId":"310478","racename":"亚冠杯","raceId":"192","raceColor":"#0000DB","date":"2016-04-19","time":"18:00","homeId":207,"hometeam":"大阪钢巴","guestId":499,"guestteam":"水原三星","statusOrigin":"0","homeScore":0,"guestScore":0,"homeHalfScore":0,"guestHalfScore":0,"homeLogoUrl":"http://pic.13322.com/icons/teams/100/207.png","guestLogoUrl":"http://pic.13322.com/icons/teams/100/499.png"}]},"result":200},{"content":{"labType":3,"bodys":[{"jumpType":2,"jumpAddr":"31","name":"1","issue":"2015147","numbers":"21,43,03,6,36,02#24","picUrl":"http://m.1332255.com/oms/upload/lottery/c15d226284b647c69f204f702490e711.png","zodiac":"???"},{"jumpType":2,"jumpAddr":"32","name":"2","issue":"20160309060","numbers":"5,4,3,0,2","picUrl":"http://m.1332255.com/oms/upload/lottery/0fc85eeee6384f44827a8ac90bfa9be0.png"},{"jumpType":2,"jumpAddr":"33","name":"3","issue":"20160222084","numbers":"3,1,6,6,8","picUrl":"http://m.1332255.com/oms/upload/lottery/ac1fe07271a84ad1b9aa3e60b6fb1529.png"},{"jumpType":2,"jumpAddr":"315","name":"15","issue":"541902","numbers":"02,07,10,06,08,01,03,04,09,05","picUrl":"http://m.1332255.com/oms/upload/lottery/444f6a933eaf48de8dafe6a789fc85ea.png"}]},"result":200}]
     * banners : {"content":[{"jumpType":0,"jumpAddr":null,"picUrl":"http://m.1332255.com/oms/upload/advert/f74d45b3433945bb8ed1eaebc342f417.png"},{"jumpType":0,"jumpAddr":null,"picUrl":"http://m.1332255.com/oms/upload/advert/f2675d1a5a59408398dfcdf0318aada9.png"},{"jumpType":0,"jumpAddr":null,"picUrl":"http://m.1332255.com/oms/upload/advert/32fccbb3bf1040f7bace8fb5911bc390.png"}],"result":200}
     */

    private int result;
    /**
     * content : [{"jumpType":2,"jumpAddr":"13","title":"足球比分","picUrl":"http://m.1332255.com/oms/upload/shortcut/e2e788590d2146fdbfbf439981833d8e.png"},{"jumpType":2,"jumpAddr":"20","title":"篮球比分","picUrl":"http://m.1332255.com/oms/upload/shortcut/23a6e97e244a441b8615aefdf8948700.png"},{"jumpType":2,"jumpAddr":"11","title":"足球数据","picUrl":"http://m.1332255.com/oms/upload/shortcut/e2688aa33fc648c9a6ab964b0fdfc1c9.png"},{"jumpType":2,"jumpAddr":"14","title":"足球视频","picUrl":"http://m.1332255.com/oms/upload/shortcut/2bb91411f14947659afc2c4b0f955698.png"},{"jumpType":2,"jumpAddr":"30","title":"彩票开奖","picUrl":"http://m.1332255.com/oms/upload/shortcut/d9f5da682f584eb09868d251548b0a86.png"},{"jumpType":2,"jumpAddr":"31","title":"香港开奖","picUrl":"http://m.1332255.com/oms/upload/shortcut/3a89651e3a6c4967826f6d21f8865a75.png"}]
     * result : 200
     */

    private MenusBean menus;
    /**
     * content : [{"jumpType":0,"jumpAddr":null,"picUrl":"http://m.1332255.com/oms/upload/advert/f74d45b3433945bb8ed1eaebc342f417.png"},{"jumpType":0,"jumpAddr":null,"picUrl":"http://m.1332255.com/oms/upload/advert/f2675d1a5a59408398dfcdf0318aada9.png"},{"jumpType":0,"jumpAddr":null,"picUrl":"http://m.1332255.com/oms/upload/advert/32fccbb3bf1040f7bace8fb5911bc390.png"}]
     * result : 200
     */

    private BannersBean banners;
    /**
     * content : {"labType":1,"bodys":[{"jumpType":2,"jumpAddr":"13","thirdId":"315148","racename":"阿根廷杯","raceId":"1183","raceColor":"#5288f4","date":"2016-04-19","time":"02:30","homeId":20758,"hometeam":"迪罗联邦","guestId":12436,"guestteam":"贝尔诺防卫","statusOrigin":"-1","homeScore":1,"guestScore":4,"homeHalfScore":0,"guestHalfScore":3,"homeLogoUrl":"http://pic.13322.com/icons/teams/100/20758.png","guestLogoUrl":"http://pic.13322.com/icons/teams/100/12436.png"},{"jumpType":2,"jumpAddr":"13","thirdId":"310478","racename":"亚冠杯","raceId":"192","raceColor":"#0000DB","date":"2016-04-19","time":"18:00","homeId":207,"hometeam":"大阪钢巴","guestId":499,"guestteam":"水原三星","statusOrigin":"0","homeScore":0,"guestScore":0,"homeHalfScore":0,"guestHalfScore":0,"homeLogoUrl":"http://pic.13322.com/icons/teams/100/207.png","guestLogoUrl":"http://pic.13322.com/icons/teams/100/499.png"}]}
     * result : 200
     */

    private List<OtherListsBean> otherLists;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public MenusBean getMenus() {
        return menus;
    }

    public void setMenus(MenusBean menus) {
        this.menus = menus;
    }

    public BannersBean getBanners() {
        return banners;
    }

    public void setBanners(BannersBean banners) {
        this.banners = banners;
    }

    public List<OtherListsBean> getOtherLists() {
        return otherLists;
    }

    public void setOtherLists(List<OtherListsBean> otherLists) {
        this.otherLists = otherLists;
    }

    public static class MenusBean {
        private int result;
        /**
         * jumpType : 2
         * jumpAddr : 13
         * title : 足球比分
         * picUrl : http://m.1332255.com/oms/upload/shortcut/e2e788590d2146fdbfbf439981833d8e.png
         */

        private List<ContentBean> content;

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean {
            private int jumpType;
            private String jumpAddr;
            private String title;
            private String picUrl;

            public int getJumpType() {
                return jumpType;
            }

            public void setJumpType(int jumpType) {
                this.jumpType = jumpType;
            }

            public String getJumpAddr() {
                return jumpAddr;
            }

            public void setJumpAddr(String jumpAddr) {
                this.jumpAddr = jumpAddr;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }
        }
    }

    public static class BannersBean {
        private int result;
        /**
         * jumpType : 0
         * jumpAddr : null
         * picUrl : http://m.1332255.com/oms/upload/advert/f74d45b3433945bb8ed1eaebc342f417.png
         */

        private List<ContentBean> content;

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean {
            private int jumpType;
            private Object jumpAddr;
            private String picUrl;

            public int getJumpType() {
                return jumpType;
            }

            public void setJumpType(int jumpType) {
                this.jumpType = jumpType;
            }

            public Object getJumpAddr() {
                return jumpAddr;
            }

            public void setJumpAddr(Object jumpAddr) {
                this.jumpAddr = jumpAddr;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }
        }
    }

    public static class OtherListsBean {
        /**
         * labType : 1
         * bodys : [{"jumpType":2,"jumpAddr":"13","thirdId":"315148","racename":"阿根廷杯","raceId":"1183","raceColor":"#5288f4","date":"2016-04-19","time":"02:30","homeId":20758,"hometeam":"迪罗联邦","guestId":12436,"guestteam":"贝尔诺防卫","statusOrigin":"-1","homeScore":1,"guestScore":4,"homeHalfScore":0,"guestHalfScore":3,"homeLogoUrl":"http://pic.13322.com/icons/teams/100/20758.png","guestLogoUrl":"http://pic.13322.com/icons/teams/100/12436.png"},{"jumpType":2,"jumpAddr":"13","thirdId":"310478","racename":"亚冠杯","raceId":"192","raceColor":"#0000DB","date":"2016-04-19","time":"18:00","homeId":207,"hometeam":"大阪钢巴","guestId":499,"guestteam":"水原三星","statusOrigin":"0","homeScore":0,"guestScore":0,"homeHalfScore":0,"guestHalfScore":0,"homeLogoUrl":"http://pic.13322.com/icons/teams/100/207.png","guestLogoUrl":"http://pic.13322.com/icons/teams/100/499.png"}]
         */

        private ContentBean content;
        private int result;

        public ContentBean getContent() {
            return content;
        }

        public void setContent(ContentBean content) {
            this.content = content;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public static class ContentBean {
            private int labType;
            /**
             * jumpType : 2
             * jumpAddr : 13
             * thirdId : 315148
             * racename : 阿根廷杯
             * raceId : 1183
             * raceColor : #5288f4
             * date : 2016-04-19
             * time : 02:30
             * homeId : 20758
             * hometeam : 迪罗联邦
             * guestId : 12436
             * guestteam : 贝尔诺防卫
             * statusOrigin : -1
             * homeScore : 1
             * guestScore : 4
             * homeHalfScore : 0
             * guestHalfScore : 3
             * homeLogoUrl : http://pic.13322.com/icons/teams/100/20758.png
             * guestLogoUrl : http://pic.13322.com/icons/teams/100/12436.png
             */

            private List<BodysBean> bodys;

            public int getLabType() {
                return labType;
            }

            public void setLabType(int labType) {
                this.labType = labType;
            }

            public List<BodysBean> getBodys() {
                return bodys;
            }

            public void setBodys(List<BodysBean> bodys) {
                this.bodys = bodys;
            }

            public static class BodysBean {
                private int jumpType;
                private String jumpAddr;
                private String thirdId;
                private String racename;
                private String raceId;
                private String raceColor;
                private String date;
                private String time;
                private int homeId;
                private String hometeam;
                private int guestId;
                private String guestteam;
                private String statusOrigin;
                private int homeScore;
                private int guestScore;
                private int homeHalfScore;
                private int guestHalfScore;
                private String homeLogoUrl;
                private String guestLogoUrl;

                public int getJumpType() {
                    return jumpType;
                }

                public void setJumpType(int jumpType) {
                    this.jumpType = jumpType;
                }

                public String getJumpAddr() {
                    return jumpAddr;
                }

                public void setJumpAddr(String jumpAddr) {
                    this.jumpAddr = jumpAddr;
                }

                public String getThirdId() {
                    return thirdId;
                }

                public void setThirdId(String thirdId) {
                    this.thirdId = thirdId;
                }

                public String getRacename() {
                    return racename;
                }

                public void setRacename(String racename) {
                    this.racename = racename;
                }

                public String getRaceId() {
                    return raceId;
                }

                public void setRaceId(String raceId) {
                    this.raceId = raceId;
                }

                public String getRaceColor() {
                    return raceColor;
                }

                public void setRaceColor(String raceColor) {
                    this.raceColor = raceColor;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public int getHomeId() {
                    return homeId;
                }

                public void setHomeId(int homeId) {
                    this.homeId = homeId;
                }

                public String getHometeam() {
                    return hometeam;
                }

                public void setHometeam(String hometeam) {
                    this.hometeam = hometeam;
                }

                public int getGuestId() {
                    return guestId;
                }

                public void setGuestId(int guestId) {
                    this.guestId = guestId;
                }

                public String getGuestteam() {
                    return guestteam;
                }

                public void setGuestteam(String guestteam) {
                    this.guestteam = guestteam;
                }

                public String getStatusOrigin() {
                    return statusOrigin;
                }

                public void setStatusOrigin(String statusOrigin) {
                    this.statusOrigin = statusOrigin;
                }

                public int getHomeScore() {
                    return homeScore;
                }

                public void setHomeScore(int homeScore) {
                    this.homeScore = homeScore;
                }

                public int getGuestScore() {
                    return guestScore;
                }

                public void setGuestScore(int guestScore) {
                    this.guestScore = guestScore;
                }

                public int getHomeHalfScore() {
                    return homeHalfScore;
                }

                public void setHomeHalfScore(int homeHalfScore) {
                    this.homeHalfScore = homeHalfScore;
                }

                public int getGuestHalfScore() {
                    return guestHalfScore;
                }

                public void setGuestHalfScore(int guestHalfScore) {
                    this.guestHalfScore = guestHalfScore;
                }

                public String getHomeLogoUrl() {
                    return homeLogoUrl;
                }

                public void setHomeLogoUrl(String homeLogoUrl) {
                    this.homeLogoUrl = homeLogoUrl;
                }

                public String getGuestLogoUrl() {
                    return guestLogoUrl;
                }

                public void setGuestLogoUrl(String guestLogoUrl) {
                    this.guestLogoUrl = guestLogoUrl;
                }
            }
        }
    }
}
