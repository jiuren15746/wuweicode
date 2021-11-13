package mysql.mvcc;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class MVCCTable {
    // key = id
    private Map<String, VersionData> dataMap = new HashMap<>();

    @Data
    static public class VersionData {
        private String id;
        private Object data;

        private final Integer createVersion;
        private Integer deleteVersion;
        private final VersionData previousVersion;

        public VersionData(String id, Object data,
                           Integer version,
                           VersionData previous) {
            this.id = id;
            this.data = data;
            this.createVersion = version;
            this.previousVersion = previous;
        }
    }

    /**
     * 插入数据。
     * @param id
     * @param data
     * @param version 创建版本
     */
    public void insert(String id, Object data, int version) {
        if (dataMap.containsKey(id)) {
            throw new RuntimeException();
        }

        VersionData versionData = new VersionData(id, data, version, null);
        dataMap.put(id, versionData);
    }

    /**
     * 查询id对应的数据，且数据版本要符合versionCond指定的条件。
     */
    public Object select(String id, Predicate<VersionData> versionCond) {
        VersionData versionData = select0(id, versionCond);
        return (versionData != null) ? versionData.getData() : null;
    }

    /**
     * 基于最新版本更新记录。并不更新最新版本的VersionData，而是插入一个新版本的VersionData。
     *
     * @param id
     * @param value
     * @param version 发起更新操作的事务版本
     * @return
     */
    public boolean update(String id, Object value, Integer version) {
        // 更新记录时，基于最新版本更新
        VersionData previousVersion = select0(id, null);
        if (null == previousVersion) {
            return false;
        }

        VersionData newVersion = new VersionData(id, value, version, previousVersion);
        previousVersion.setDeleteVersion(version);
        dataMap.put(id, newVersion);
        return true;
    }

    /**
     * 查询id对应的数据，且数据版本要符合versionCond指定的条件。
     * 如果不指定versionCond，总是查询最新版本的数据。
     */
    private VersionData select0(String id, Predicate<VersionData> versionCond) {
        VersionData versionData = dataMap.get(id);
        if (null == versionData) {
            return null;
        }

        if (versionCond == null) {
            return versionData;
        }

        for (; versionData != null; versionData = versionData.getPreviousVersion()) {
            if (versionCond.test(versionData)) {
                return versionData;
            }
        }
        return null;
    }

}
