package util;

import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import java.io.File;

public class IPUtils {
    /**
     * 根据IP获取地址
     *
     * @return 国家|区域|省份|城市|ISP
     */
    public static String getAddress(String ip) throws Exception {
        return getAddress(ip, DbSearcher.BTREE_ALGORITHM);
    }

    /**
     * 根据IP获取地址
     *
     * @param ip
     * @param algorithm 查询算法
     * @return 国家|区域|省份|城市|ISP
     * @see DbSearcher
     * DbSearcher.BTREE_ALGORITHM; //B-tree
     * DbSearcher.BINARY_ALGORITHM //Binary
     * DbSearcher.MEMORY_ALGORITYM //Memory
     */
    public static String getAddress(String ip, int algorithm) throws Exception {
        if (!Util.isIpAddress(ip)) {
            return "";
        }
        String dbPath = IPUtils.class.getResource("/db/ip2region.db").getPath();
        File file = new File(dbPath);
        if (!file.exists()) {
            return "";
        }
        DbSearcher searcher = new DbSearcher(new DbConfig(), dbPath);
        DataBlock dataBlock;
        switch (algorithm) {
            case DbSearcher.BTREE_ALGORITHM:
                dataBlock = searcher.btreeSearch(ip);
                break;
            case DbSearcher.BINARY_ALGORITHM:
                dataBlock = searcher.binarySearch(ip);
                break;
            case DbSearcher.MEMORY_ALGORITYM:
                dataBlock = searcher.memorySearch(ip);
                break;
            default:
                return "";
        }

        searcher.close();
        return dataBlock.getRegion();
    }
}
