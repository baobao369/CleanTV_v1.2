package zone.vgs.tvsach.dulieu

import org.json.JSONArray
import org.json.JSONObject

/**
 * Thông tin chi tiết một bản sao lưu APK trên thiết bị TV trước khi bị gỡ bỏ.
 * Lưu trữ tại: /sdcard/CleanTV_Backups/<pkg>/info.json
 */
data class ThongTinSaoLuuApk(
    val pkg: String,
    val tenApp: String,
    val thoiGian: Long,
    val laHeThong: Boolean,
    val kichThuocMb: Double = 0.0,
    val danhSachApk: List<String> = emptyList(),
    val ghiChu: String = "",
    val daGoKhoiTv: Boolean = true
) {
    fun toJson(): JSONObject = JSONObject().apply {
        put("pkg", pkg)
        put("tenApp", tenApp)
        put("thoiGian", thoiGian)
        put("laHeThong", laHeThong)
        put("kichThuocMb", kichThuocMb)
        put("danhSachApk", JSONArray(danhSachApk))
        put("ghiChu", ghiChu)
        put("daGoKhoiTv", daGoKhoiTv)
    }

    companion object {
        fun tuJson(o: JSONObject): ThongTinSaoLuuApk {
            val list = o.optJSONArray("danhSachApk")?.let { a -> (0 until a.length()).map { a.getString(it) } } ?: emptyList()
            val pkg = o.getString("pkg")
            return ThongTinSaoLuuApk(
                pkg = pkg,
                tenApp = o.optString("tenApp").ifBlank { tenDep(pkg) },
                thoiGian = o.optLong("thoiGian", System.currentTimeMillis()),
                laHeThong = o.optBoolean("laHeThong", false),
                kichThuocMb = o.optDouble("kichThuocMb", 0.0),
                danhSachApk = list,
                ghiChu = o.optString("ghiChu", ""),
                daGoKhoiTv = o.optBoolean("daGoKhoiTv", true)
            )
        }
    }
}
