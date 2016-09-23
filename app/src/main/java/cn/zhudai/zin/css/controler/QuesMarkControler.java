package cn.zhudai.zin.css.controler;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by admin on 2016/8/31.
 */
public class QuesMarkControler {
    private static void showTipDialog(Context context,String content) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(content);
        builder.setPositiveButton("确定", null);
        builder.show();
    }
    public static void showStarlevelQuesmarkTipDialog(Context context){
        String tip = "星级是对客户贷款资质的客观评价\n" +
                "\n" +
                "0星：系统默认的未了解过信息的客户；\n" +

                "1星：本人或身边朋友无可贷点的客户；\n" +

                "2星：本人或身边朋友有可贷点但暂时进不了件的客户；\n" +

                "3星：本人或身边朋友有可贷点的客户；\n" +

                "4星：有可贷点，且马上需要或条件优质的客户（存量客户默认4星）；\n";
        showTipDialog(context,tip);
    }
    public static void showstatelQuesmarkTipDialog(Context context){
        String tip = "状态是客户申请所处的环节\n" +
                "\n" +
                "0.待跟进：尝试联系，但未了解客户信息；\n" +

                "1.贷款资质不符：该客户或身边人士无可贷点，或同行、骚扰等无效申请；\n" +

                "2.待签约：联系后，并判定该客户或身边人士有可贷点；\n" +

                "3.已签约：已上门签约；\n" +

                "4.审核中：客户已经完成进件，银行审核中，请备注进件银行、申请金额、审核进度；\n" +

                "5.银行已放款：银行审批结束，批放款成功，请备注放款银行、到账金额；\n" +

                "6.银行已拒绝：银行审批结束，贷款被拒（包含初审），请备注被拒的原因；\n";
        showTipDialog(context,tip);
    }
}
