package constant;

public class Constant {
    public static final int DURATION = 4;
    public static final int BLOCKS_WIDTH = 52;
    public static final int BLOCKS_HEIGHT = 28;
    public static final int BLOCKS_SIZE = 25; // 32; should be odd for better performance
    public static final int SCREEN_WIDTH = 52 * BLOCKS_SIZE;
    public static final int SCREEN_HEIGHT = 28 * BLOCKS_SIZE;
//thời gian AutoAgv đợi để nhận/dỡ hàng khi đến đích
//    public static getLateness = (x: number) => 5*x; //hàm tính chi phí thiệt hại nếu đến quá sớm hoặc quá trễ
//    public static get SAFE_DISTANCE() : number { return 46; }
//    public static get DELTA_T() : number { return 10; }
    public static ModeOfPathPlanning MODE() { return ModeOfPathPlanning.FRANSEN; }
    
    public static String secondsToHMS(Double seconds) {
        var h = Math.floor(seconds % (3600*24) / 3600);
        var m = Math.floor(seconds % 3600 / 60);
        var s = Math.floor(seconds % 60);
        
        var hDisplay = h >= 10 ? h : ("0" + h);
        var mDisplay = m >= 10 ? m : ("0" + m);
        var sDisplay = s >= 10 ? s : ("0" + s);
        return hDisplay + ":" + mDisplay + ":" + sDisplay;
    }
}
