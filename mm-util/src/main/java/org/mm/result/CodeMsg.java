package org.mm.result;

public class CodeMsg {
	private int code;
    private String msg;

    //通用的錯誤碼
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服務端異常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "參數驗證異常：%s");
    public static CodeMsg ACCESS_LIMIT_REACHED= new CodeMsg(500104, "訪問高峰期，请稍等！");
    //登錄模組 5002XX
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已經失效");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登錄密碼不能為空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手機號碼不能為空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手機號碼格式錯誤");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "手機號碼不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密碼錯誤");
    public static CodeMsg PRIMARY_ERROR = new CodeMsg(500216, "主鍵衝突");

    //商品模組 5003XX

    //訂單模組 5004XX
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500400, "訂單不存在");

    //秒殺模組 5005XX
    public static CodeMsg SECKILL_OVER = new CodeMsg(500500, "商品己經秒殺完畢");
    public static CodeMsg REPEATE_SECKILL = new CodeMsg(500501, "不能重覆秒殺");

    private CodeMsg() {
    }

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 返回带参数的错误码
     * @param args
     * @return
     */
    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }

}
