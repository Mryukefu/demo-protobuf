package com.example.demoprotobuf.utils;



public enum ResultEnum {

    // 请求成功
    SUCCESS(200, "请求成功"),
    FAIL(100, "请求失败"),
    PARAMETER_IS_NULL(300, "请求参数为空"),
    EXCEPTION(400, "请求处理异常"),
    SQLEXCEPTION(401, "SQL处理异常"),
    SIGN_ERROR(500, "签名失败"),

    /**
     * 100段
     **/
    REQUEST_PARAMETER_NULL(100, "请求参数为空"),
    REQUEST_PARAMETER_ERROR(101, "请求参数不合理"),
    REQUEST_SEND_SMS_ERROR(102, "发送验证码失败"),
    REQUEST_PARAMETER_IS_WRONG(103, "请求参数不合法"),
    RESULT_IS_NULL(104, "查询结果为空"),
    DATA_IS_EXIST(105, "数据已经存在"),
    REQUEST_OS_type_IS_NOT_EXIST(106, "缺少设备类型"),
    WITHOUT_EASY_LOGIN(107, "没有一键等"),
    DRAW_ACT_PRIZE_ERROR(108, "当前奖品类型没有谢谢惠顾商品"),
    COLOR_PARAMETER_ERROR(109, "顔色参数错误"),
    APP_BUZ_EXCEPTION(110, "业务异常"),

    /**
     * 300段
     **/
    PRODUCT_CONF_NOT_FIND(300, "PRODUCT不存在"),
    PRODUCT_CONF_IS_INIT_DISABLE(301, "PRODUCT禁止初始化"),
    PRODUCT_CONF_IS_REG_DISABLE(302, "PRODUCT禁止新增"),
    PRODUCT_CONF_IS_PAY_DISABLE(303, "PRODUCT禁止下单"),
    CHANNEL_CONF_NOT_FIND(304, "CHANNEL不存在"),
    CHANNEL_CONF_IS_INIT_DISABLE(305, "CHANNEL禁止初始化"),
    CHANNEL_CONF_IS_REG_DISABLE(306, "CHANNEL禁止新增"),
    CHANNEL_CONF_IS_PAY_DISABLE(307, "CHANNEL禁止下单"),
    CHANNEL_IS_EXIST(308, "产品渠道已存在"),
    CHANNEL_PAY_CON_IS_ERROR(309, "产品渠道支付参数配置异常"),
    CHANNEL_NOTICE_CON_IS_ERROR(310, "产品渠道公告配置异常"),
    CHANNEL_CUSTOM_CON_IS_ERROR(311, "产品渠道客服配置异常"),
    PRODUCT_NO_FIND(312, "产品未找到"),
    SDK_CHANNEL_NAME_IS_EXIST(314, "渠道商名称已存在"),
    SDK_CHANNEL_REMARK_IS_EXIST(315, "渠道商真名已存在"),
    CHANNEL_PAY_TYPE_IS_ERROR(316, "渠道支付方式异常"),
    PRODUCT_CONF_IS_LOGIN_DISABLE(317, "PRODUCT禁止登录"),
    CHANNEL_CONF_IS_LOGIN_DISABLE(318, "CHANNEL禁止登录"),
    SDK_CHANNEL_NAME_NOT_EXIST(319, "渠道商名称不存在"),
    CHANNEL_LOGIN_TYPE_IS_ERROR(320, "渠道登录方式异常"),

    /**
     * 400段
     **/
    INTERFACE_NO_RESULT(400, "接口无返回"),
    INTERFACE_FAILED_RESULT(401, "接口返回失败状态"),
    INTERFACE_ID_NOT_EQUAL(402, "请求ID与响应ID不一致"),
    INTERFACE_RESPONSE_DATA_NULL(403, "响应data或data中相关数据为空"),

    /**
     * 500段
     **/
    SERVER_SQL_ERROR(500, "服务端SQL异常"),
    SERVER_CODE_RUN_ERROR(501, "服务端异常"),
    CACHING(502, "正在缓存"),
    TIMING_TASK_EXCEPTION(503, "定时任务异常"),

    /**
     * 600段
     **/
    USER_IS_NOT_EXIST(600, "用户不存在"),
    USER_PASSWORD_ERROR(601, "用户密码错误"),
    USER_SUB_EXCEED(602, "小号注册数过多"),
    USER_PHONE_IS_EXIT(603, "手机号码已经被注册"),
    USER_EXIT_PHONE(604, "账号没有绑定手机号码"),
    USER_CODE_IS_ERROR(605, "验证码错误"),
    USER_CODE_IS_EXIT(606, "验证码已存在"),
    USER_CODE_IS_NOT_EXIT(607, "验证码不存在"),
    USER_IS_EXIT(608, "用户已存在"),
    USER_STOP_REG_FOR_IP(609, "禁止此IP注册"),
    USER_STOP_REG_FOR_DEVICE(610, "禁止此设备注册"),
    USER_NEED_REGISTER(611, "登陆失败，用户需重新注册"),
    USER_ID_CARD_AUTH_ERROR(612, "用户实名认证失败"),
    USER_ID_CARD_EXIT(613, "用户已经实名已存在"),
    USER_ID_CARD_IS_WRONG(614, "用户身份证不合法"),
    USER_STOP_LOGIN(615, "禁止此用户登陆"),
    USER_DATA_NOT_EXIT(616, "用户角色数据不存在"),
    USER_DATA_IS_EXIT(617, "用户角色数据已存存在"),
    ADMIN_USER_NOT_EXIT(618, "admin用户不存在"),
    ADMIN_PASSWORD_IS_ERROR(619, "密码错误"),
    USER_PHONE_IS_BINDING(620, "手机号码已经被绑定"),
    USER_PHONE_IS_NOT_MATCH(621, "手机号码账号不匹配"),
    USER_IS_NOT_AUTH_OLD_PHONE(622, "用户未认证原绑定号码"),
    USER_GAME_WALLET_NOT_EXIT(623, "用户游戏钱包不存在"),
    USER_TOKEN_NOT_EXIT(624, "用户游戏钱包不存在"),
    USER_LOGIN_OUT(625, "用户已登出"),
    USER_COIN_LOCK(626, "账户处理中，请稍后下单充值"),
    USER_GAME_WALLET_LOCK(627, "游戏币账户处理中，请稍后下单"),
    REAL_NAME_WAITING(628, "实名认证中"),

    /**
     * 700段
     **/
    ORDER_IS_NOT_EXIT(700, "订单不存在"),
    ORDER_USER_COIN_NOT_ENOUGH(701, "用户臣币余额不足"),
    ORDER_PAY_CHANNEL_NOT_FIND(702, "支付渠道不存在"),
    ORDER_PAY_CHANNEL_PARAM_ERROR(703, "支付渠道配置信息错误"),
    ORDER_APY_CREATE_ERROR(704, "下单失败，请稍后再试"),
    ORDER_APY_EXCEPTION(705, "回调订单处理异常"),
    ORDER_APY_SIGN_ERROR(706, "回调订单签名失败"),
    ORDER_APY_STATUS_ERROR(707, "回调订单状态不正确"),
    ORDER_APY_CALLBACK_DATA_NULL(708, "回调订单数据为空"),
    ORDER_APY_AMOUNT_IS_ERROR(709, "订单金额不合法"),
    ORDER_APY_CALLBACK_ORDER_NOT_EXIST(710, "回调订单不存在"),
    ORDER_APY_AUTH_ERROR(711, "消费验证错误"),
    ORDER_ERROR(712, "下单失败，订单异常"),
    ORDER_DIS_AMOUNT_ERROR(713, "下单失败，折扣异常"),
    ORDER_IS_VALIDATED(714, "该笔订单已被验证过"),
    ORDER_PAY_CHANNEL_TYPE_CONFIG_ERROR(715, "下单支付方式配置异常"),
    ORDER_PAY_CHANNEL_TYPE_IS_CLOSE(716, "支付方式不存在或者已关闭"),
    ORDER_USER_GAME_WALLET_NOT_ENOUGH(717, "游戏币余额不足"),
    ORDER_PLATFORM_COIN_NOT_ENOUGH(718, "用户平台币余额不足"),
    ORDER_APPLE_IS_ERROR(719, "下单失败，苹果计费点配置异常"),
    ORDER_PLATFORM_GAME_COIN_NOT_ENOUGH(720, "用户游戏币不足"),
    /**
     * 800段
     **/
    COMPANY_IS_NULL(810, "样式不存在"),

    /**
     * 900段
     **/
    DELETE_CHANNEL_FAIL1(901, "删除渠道失败，该渠道下有子渠道"),
    DELETE_CHANNEL_FAIL2(902, "删除渠道失败，该渠道下有用户信息"),
    DELETE_CHANNEL_SUB_FAIL(903, "删除子渠道失败，该子渠道下有用户信息"),

    /**
     * 1100段 产品类错误
     **/
    GAME_IS_NULL(1100, "无效的APPID"),
    GAME_STOP_REG(1101, "此产品禁止注册"),
    GAME_STOP_LOG(1102, "此产品禁止登陆"),
    GAME_STOP_PAY(1103, "此产品禁止下单"),
    GAME_STOP_INIT(1104, "此产品已经关闭"),

    /**
     * 1200段 渠道SDK类错误
     **/
    CHANNEL_IS_NULL(1200, "无效的渠道ID"),
    CHANNEL_STOP_REG(1201, "此渠道禁止注册"),
    CHANNEL_STOP_LOG(1202, "此渠道禁止登陆"),
    CHANNEL_STOP_PAY(1203, "此渠道禁止下单"),
    CHANNEL_STOP_INIT(1204, "此渠道已经关闭"),
    //SUB_CHANNEL_IS_NULL(1205, "平台此子渠道不存在"),
    SUB_CHANNEL_STOP_REG(1206, "此子渠道禁止注册"),
    SUB_CHANNEL_STOP_LOG(1207, "此子渠道禁止登陆"),
    SUB_CHANNEL_STOP_PAY(1208, "此子渠道禁止下单"),
    SUB_CHANNEL_STOP_INIT(1209, "此子渠道已经关闭"),
    SDK_CHANNEL_CONFIG_ERROR(1210, "渠道商参数配置异常"),

    /** 1300段 用户类错误 */
//    USER_IS_NOT_EXIST(1300, "用户不存在"),

    /**
     * 1400段 配置错误
     */
    PARAM_CONFIG_ERROR(1400, "产品和产品配置异常"),

    /**
     * 1500段 验证错误
     */
    SDK_CHANNEL_USER_VALIDATE_TIMEOUT(1500, "用户验证超过有效时间"),
    SDK_CHANNEL_USER_SIGN_ERROR(1501, "用户验证签名失败"),
    SDK_CHANNEL_API_NO_RETURN(1502, "渠道接口无返回"),
    SDK_CHANNEL_VERSION_ERROR(1503, "未找到对应版本登录验证方法"),
    SDK_CHANNEL_USER_VALIDATE_ERROR(1504, "用户验证返回失败"),

    /**
     * 1600段
     */

    FILE_SIZE_EXCEED_LIMIT(1600, "文件大小超过限制"),
    FILE_FORMAT_IS_ERROR(1601, "文件格式错误"),

    /**
     * 1700段 验证错误
     */
    HAVE_ALREADY_RECEIVED(1700, "已经授权过了"),
    UNAUTHORIZED(1701, "未授权"),
    WITHOUT_GAME_CONFIG(1702, "没有游戏配置"),
    GAME_CONFIG_ING(1703, "该游戏已经配置过了"),
    NOT_SUFFICIENT_FUNDS(1704, "余额不足"),
    WITHDRAWAL_FAIL(1705, "提现失败"),
    AUTHORIZATION_SUCCESS(1706, "授权成功"),
    SAWING_FAIL(1707, "冲榜任务的排名应该和奖励数目一样"),
    WITHOUT_DEVICEID(1708, "请重新刷新后再登录"),
    NOT_LOGIN(1709, "请登录"),
    NOT_LOGIN_STAUTS(1710, "展示请登录状态"),
    LOGIN_AGAIN(1711, "请重新登录"),
    USER_IS_XXX(999, ""),

    /**
     * 1800段 抽奖
     */
    WITHOUT_CNT(1800, "没有次数了"),
    REPEAT_CLICK(1801, "不能重复点击"),
    WITHOUT_WELFARE_PAYMENTS(1802, "福利金不足"),
    DRAW_END(1803, "活动已经结束了"),
    DRAW_NOT_START(1804, "活动未开始了"),
;
    private int code;
    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
