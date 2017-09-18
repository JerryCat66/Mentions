package com.fenguo.library.http;

import android.content.Context;

/**
 * 类/接口注释
 *
 * @author zhangyb@ifenguo.com
 * @createDate 2015年5月25日
 */
public enum StateCode {

    /**
     * 调用成功,2000000
     */
    C2000000("2000000", "调用成功"),

    /**
     * 通用的失败状态码,5000001
     */
    C5000001("5000001", "通用的失败状态码"),

    /**
     * 数据未找到,5000002
     */
    C5000002("5000002", "数据未找到"),

    /**
     * 参数绑定不正确,5000003
     */
    C5000003("5000003", "参数绑定不正确"),

    /**
     * 用户名或密码错误,5000102
     */
    C5000102("5000102", "用户名或密码错误"),

    C5000444("5000444","您的密码可能已遗失，请到忘记密码里重新设计密码"),

    /**
     * 会话信息失效,5000103
     */
    C5000103("5000103", "会话信息失效"),

    /**
     * 用户未登录,5000104
     */
    C5000104("5000104", "用户未登录"),

    /**
     * 输入的密码跟原密码不一致,5000105
     */
    C5000105("5000105", "输入的密码跟原密码不一致"),

    /**
     * 请求非POST方法,5000111
     */
    C5000111("5000111", "请求非POST方法"),

    /**
     * 参数不完整,5000112
     */
    C5000112("5000112", "参数不完整"),

    /**
     * 两次密码不一致,5000113
     */
    C5000113("5000113", "两次密码不一致"),

    /**
     * 用户名错误,5000182
     */
    C5000182("5000182", "用户名不存在"),

    /**
     * 密码错误,5000192
     */
    C5000192("5000192", "密码错误"),

    /**
     * 该账号已被删除,5000222
     */
    C5000222("5000222", "该账号已被删除"),

    /**
     * C5000114,手机号码不存在
     */
    C5000114("5000114", "手机号码不存在"),

    /**
     * C5000115,手机号码已注册
     */
    C5000115("5000115", "手机号码已注册"),

    /**
     * C5000116,验证码错误
     */
    C5000116("5000116", "验证码错误"),

    /**
     * C5000117,验证码已过期
     */
    C5000117("5000117", "验证码已过期"),

    /**
     * 5000120,请先获取验证码
     */
    C5000120("5000120", "请先获取验证码"),

    /**
     * 5000118,修改密码手机跟用户手机不一致
     */
    C5000118("5000118", "修改密码手机跟用户手机不一致"),

    /**
     * 5000119,用户不存在
     */
    C5000119("5000119", "用户不存在"),

    /**
     * 5000121,未绑定的银行卡
     */
    C5000121("5000121", "未绑定的银行卡"),

    /**
     * 5000122,非本人身份证
     */
    C5000122("5000122", "非本人身份证或未绑定身份证"),

    /**
     * C5000144,签名失败
     */
    C5000144("5000144", "签名失败"),


    /******************* 保险相关 ********************************************/
    /**
     * 5000200,货运保险不存在
     */
    C5000200("5000200", "货运保险不存在"),

    /******************* 钱包相关 ********************************************/
    /**
     * 5003000,钱包密码错误
     */
    C5000300("5003000", "钱包密码错误"),
    /**
     * 5003001,银行卡验证不通过
     */
    C5000301("5003001", "银行卡验证不通过"),
    /**
     * 5003002,钱包密码未设置
     */
    C5000302("5003002", "钱包密码未设置"),

    /**
     * 5003003,对方信息未审核，不可向其转账
     */
    C5000303("5003003", "对方信息未审核，不可向其转账"),
    /**
     * 5003004,第三方转账错误，未转账
     */
    C5000304("5003004", "第三方转账错误，未转账成功"),
    /**
     * 5003005,请绑定手机号码
     */
    C5000305("5003005", "请绑定手机号码"),
    /**
     * 5003006,请绑定银行卡
     */
    C5000306("5003006", "请绑定银行卡，若忘记钱包密码请咨询客服"),
    /**
     * 5003007,钱包余额不足
     */
    C5000307("5003007", "钱包余额不足"),

    /**
     * 参数不完整,5000308
     */
    C5000308("5000308", "参数不正确"),

    /**
     * 5000309，冻结信息费失败
     */
    C5000309("5000309", "冻结信息费失败"),

    /**
     * 5000310，检查钱包余额是否大于信息费方法调用异常
     */
    C5000310("5000310", "检查钱包余额是否大于信息费方法调用异常"),

    /**
     * 5000311，冻结信息费方法调用异常
     */
    C5000311("5000311", "冻结信息费方法调用异常"),

    /**
     * 5000312，解冻车主信息费转给货主方法调用异常
     */
    C5000312("5000312", "解冻车主信息费转给货主方法调用异常"),

    /**
     * 5000313，取消订单，解冻信息费回转给车主方法调用异常
     */
    C5000313("5000313", "取消订单，解冻信息费回转给车主方法调用异常"),

    /**
     * 5000314，取消车主订单失败
     */
    C5000314("5000314", "取消车主订单失败"),

    /**
     * 5000315，更新钱包金额失常
     */
    C5000315("5000315", "更新钱包金额异常"),

    /**
     * 5000316，更新资金动态失常
     */
    C5000316("5000316", "更新资金动态异常"),

    /**
     * 5000317，更新充值动态异常
     */
    C5000317("5000317", "更新充值动态异常"),

    /**
     * 5000318，钱包参数参数错误
     */
    C5000318("5000318", "钱包参数参数错误"),

    /**
     * 5000319，不能转账给本人
     */
    C5000319("5000319", "不能转账给本人"),

    /**
     * 5000320，审核不通过
     */
    C5000320("5000320", "审核不通过"),

    /**
     * 5000321，审核网络异常
     */
    C5000321("5000321", "审核网络异常"),

    /**
     * 钱包未注册,5000002
     */
    C5000322("5000322", "钱包未注册"),

    /**
     * 钱包未注册,5000323
     */
    C5000323("5000323", "记录绑定银行卡记录不正常, 请稍后重新绑定"),

    /**
     * 5000324,您好，您钱包余额不足以冻结交易所需质保金，请充值后再提交订单。
     */
    C5000324("5003024", "您好，您钱包余额不足以冻结交易所需质保金，请充值后再提交订单。"),

    /**
     * 5000325,您好，您钱包余额不足以支付交易所需信息费，请充值后再提交订单。
     */
    C5000325("5003025", "您好，您钱包余额不足以支付交易所需信息费，请充值后再提交订单。"),

    /**
     * 5000326,已绑定此银行卡。
     */
    C5000326("5003026", "已绑定此银行卡"),

    /**
     * 5000327,手机号码已绑定钱包。
     */
    C5000327("5003027", "手机号码已绑定钱包"),

    /**
     * 5000328,手机号码已绑定钱包。
     */
    C5000328("5003028", "手机号码已绑定钱包"),

    /**
     * 5000329,每天充值5次。
     */
    C5000329("5000329", "每天最多充值5次"),

    /**
     * 5000330,充值单笔最高金额(含)20000元。
     */
    C5000330("5000330", "充值单笔最高金额(含)20000元"),

    /**
     * 5000331,每天提现5次。
     */
    C5000331("5000331", "每天最多提现5次"),

    /**
     * 5000332,提现单笔最高金额(含)2000元。
     */
    C5000332("5000332", "提现单笔最高金额(含)2000元"),
    /******************* 车源相关 **********************/
    /**
     * 5000601,车源重发未能相隔至少20分钟
     */
    C5000601("5000601", "车源重发未能相隔至少20分钟"),

    /******************* 订单相关 ********************************************/
    /**
     * 权限冲突
     */
    C5000401("5000401", "权限冲突"),
    /**
     * 车主不是在处理自己的订单
     */
    C5000402("5000402", "车主不是在处理自己的订单"),
    /**
     * 订单未确认，不可查车辆轨迹
     */
    C5000403("5000403", "订单未确认，不可查车辆轨迹"),
    /**
     * 个人信息不完善
     */
    C5000201("5000201", "个人信息不完善"),
    /**
     * 质保金不够
     */
    C5000202("5000202", "质保金不够"),

    /**
     * 车主有未完成的订单，暂时不能下单
     */
    C5000203("5000203", "车主有未完成的订单，暂时不能下单"),
    /**
     * 车主钱包余额小于信息费
     */
    C5000204("5000204", "车主钱包余额小于信息费"),

    /**
     * 5000205 车主订单操作不符流程
     */
    C5000205("5000205", "车主订单操作不符流程"),

    /**
     * 5000206 提交的客户ID不是车主ID
     */
    C5000206("5000206", "提交的客户ID不是车主ID"),

    /**
     * 5000207 车主确认订单失败
     */
    C5000207("5000207", "车主确认订单失败"),

    /**
     * 5000208 车主完成订单失败
     */
    C5000208("5000208", "车主完成订单失败"),

    /**
     * 5000209 车主新增订单失败
     */
    C5000209("5000209", "车主新增订单失败"),

    /**
     * 5000210 车主修改个人统计指标表异常
     */
    C5000210("5000210", " 车主修改个人统计指标表异常"),


    /**
     * 5000211 车主修改货源状态异常
     */
    C5000211("5000211", "  车主修改货源状态异常"),

    /**
     * 5000212  该车源已在其他订单中处理，不能再确认
     */
    C5000212("5000212", "该车源已在其他订单中处理，不能再确认"),

    /**
     * 5000213  该货源已在其他订单中处理，不能再确认
     */
    C5000213("5000213", "该货源已在其他订单中处理，不能再确认"),


    /**
     * 重发间隔时间小于20分钟
     */
    C5000501("5000501", "重发间隔时间小于20分钟"),
    /**
     * 货源已经被预订
     */
    C5000404("5000404", "货源已经被预订"),
    /**
     * 货源已经被预订（完成订单）
     */
    C5000406("5000406", "货源已经被预订"),
    /**
     * 查看货源是否已成交失败
     */
    C5000405("5000405", "检查货源是否已成交失败"),
    /**
     * 取消订单失败
     */
    C5000502("5000502", "取消订单失败"),


    /******************* 我的关注相关 ********************************************/
    /**
     * 关注已存在
     */
    C5000701("5000701", "已关注"),
    /**
     * 关注不存在
     */
    C5000702("5000702", "未关注"),

    /**
     * 找不到车主或者货主
     */
    C5000703("5000703", "找不到车主或者货主"),
    /**
     * 关注不存在
     */
    C5000704("5000704", "不能关注自己"),

    /******************* 我的评价相关 ********************************************/
    /**
     * 车源/货源/订单不存在用户
     */
    C5000801("5000801", "车源/货源/订单不存在用户"),
    /**
     * 已添加评价
     */
    C5000802("5000802", "已添加评价"),
    /**
     * 修改订单状态出异常
     */
    C5000803("5000803", "修改订单状态出异常"),
    /**
     * 车源不存在
     */
    C5000804("5000804", "车源不存在"),
    /**
     * 货源不存在
     */
    C5000805("5000805", "货源不存在"),

    /**
     * 订单不存在
     */
    C5000806("5000806", "订单不存在"),

    /******************* 推送相关 ********************************************/
    /**
     * 推送失败
     */
    c5001001("5001001", "推送失败"),
    /******************* 定位相关 ********************************************/
    /**
     * 车源/货源/订单不存在用户
     */
    C5000901("5000901", "属于该手机号码的车主不存在"),
    /**
     * 车源/货源/订单不存在用户
     */
    C5000902("5000902", "车主未授权定位，已发送授权定位请求"),


    /******************* 信用标普相关 ********************************************/
    /**
     * 信用标普通用异常
     */
    C5002000("5002000", "信用标普通用异常"),

    /**
     * 找不到用户
     */
    C5002001("5002001", "找不到用户"),

    /******************* 拨打电话相关 ********************************************/
    /**
     * 已经拨打了20位用户
     */
    C5002003("5002003", "今天已经拨打了20位用户"),
    /**
     * 请先拨打电话
     */
    C5002004("5002004", "您需先拨打电话之后才可以评价！"),
    /******************* 全局错误状态码 ********************************************/

    /**
     * 0000404,404错误
     */
    C0000404("0000404", "404错误"),

    /**
     * 0000500,500错误
     */
    C0000500("0000500", "500错误"),
/******************* 货源 ********************************************/
    /**
     * 修改货源状态为已定失败
     */
    C5001101("5001101", "修改货源状态为已定失败"),
    /**
     * 删除货源失败
     */
    C5001102("5001102", "删除货源失败"),

    /**************************用户相关*************************************/

    /**
     * 用户未绑定身份证
     */
    C5001200("5001200", "用户未绑定身份证"),


    /**************************保险相关*************************************/

    /**
     * 保险购买失败，请稍后重试
     */
    C5001300("5001300", "签单日期不能在起运日期之后！"),
    /**
     * 保险购买失败，货物类型错误
     */
    C5001301("5001301", "保险购买失败，货物类型错误"),
    /**
     * 5001302,运单号已存在
     */
    C5001302("5001302", "运单号已存在"),
    /**************************身份验证相关*************************************/

    /**
     * 更新验证状态失败
     */
    C5001400("5001400", "更新验证状态失败"),


    /******************* 内部错误 ********************************************/

    /**
     * 内部错误
     */
    C5000000("5000000", "false");


    /**
     * 值
     */
    private String status;

    /**
     * 中文值
     */
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private StateCode() {
    }

    StateCode(String status, String message) {

        this.status = status;
        this.message = message;
    }

    public static String getMessage(Context context, String status) {
        String message = null;
        for (StateCode stateCode : values()) {
            if (stateCode.getStatus().equals(status)) {
                return stateCode.getMessage();
            }
        }
        return "待添加的错误码";
    }

}
