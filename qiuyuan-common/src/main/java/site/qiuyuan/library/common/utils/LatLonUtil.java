package site.qiuyuan.library.common.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.4.7
 */
public class LatLonUtil {

    public static double pi = 3.141592653589793 * 3000.0 / 180.0;

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
     *
     * @param lat 纬度
     * @param lon 经度
     */
    public static LocateInfo gcj02ToBd09(double lon, double lat) {
        double z = Math.sqrt(lon * lon + lat * lat) + 0.00002 * Math.sin(lat * pi);
        double theta = Math.atan2(lat, lon) + 0.000003 * Math.cos(lon * pi);
        return new LocateInfo(z * Math.cos(theta) + 0.0065, z * Math.sin(theta) + 0.006);
    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法   将 BD-09 坐标转换成GCJ-02 坐标
     *
     * @param lon 经度
     * @param lat 纬度
     */
    public static LocateInfo bd09ToGcj02(double lon, double lat) {
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
        return new LocateInfo(z * Math.cos(theta), z * Math.sin(theta));
    }

    @Setter
    @Getter
    public static class LocateInfo {
        private double longitude;
        private double Latitude;
        private boolean isChina;

        public LocateInfo() {
        }

        public LocateInfo(double longitude, double latitude) {
            this.longitude = longitude;
            Latitude = latitude;
        }

    }
}
