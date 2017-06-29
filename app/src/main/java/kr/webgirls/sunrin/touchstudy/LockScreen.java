package kr.webgirls.sunrin.touchstudy;

import android.content.DialogInterface;
import android.app.AlertDialog;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sujin on 2016. 6. 6..
 */

public class LockScreen extends Activity implements View.OnClickListener {

    //static ArrayList<Integer> mistakesNumber;
    static ArrayList<Question> questions;
    int question_Number, question_displayNumber;
    TextView tv_Question;
    RadioButton rb_btn1, rb_btn2, rb_btn3, rb_btn4;
    Button btn_call, btn_message, btn_camera;
    DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_screen);


        mDBHelper = new DBHelper(this);
        questions = mDBHelper.getAllQuestions();
        question_Number = questions.size();
        question_displayNumber = (int) (Math.random() * question_Number + 1);

        tv_Question = (TextView) findViewById(R.id.tv_Question);
        rb_btn1 = (RadioButton) findViewById(R.id.radioButton);
        rb_btn2 = (RadioButton) findViewById(R.id.radioButton2);
        rb_btn3 = (RadioButton) findViewById(R.id.radioButton3);
        rb_btn4 = (RadioButton) findViewById(R.id.radioButton4);
        btn_call = (Button) findViewById(R.id.button_call);
        btn_message = (Button) findViewById(R.id.button_message);
        btn_camera = (Button) findViewById(R.id.button_camera);

        rb_btn1.setOnClickListener(this);
        rb_btn2.setOnClickListener(this);
        rb_btn3.setOnClickListener(this);
        rb_btn4.setOnClickListener(this);
        btn_call.setOnClickListener(this);
        btn_message.setOnClickListener(this);
        btn_camera.setOnClickListener(this);


        if (DBHelper.checkUpdate) addTable();
        try {
            setScreen();
        } catch (Exception e) {
            Log.d("***TAG***", e.getMessage());
        }

    }

    public void setScreen() {
        tv_Question.setText(questions.get(question_displayNumber - 1).question);
        rb_btn1.setText(questions.get(question_displayNumber - 1).answer1);
        rb_btn2.setText(questions.get(question_displayNumber - 1).answer2);
        rb_btn3.setText(questions.get(question_displayNumber - 1).answer3);
        rb_btn4.setText(questions.get(question_displayNumber - 1).answer4);
    }

    //해설창 띄우기
    public void explainDialog(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 제목 설정
        if (i == 1) builder.setTitle("맞았습니다");
        else if (i == 0) {
            builder.setTitle("틀렸습니다");
        } else builder.setTitle("정답여부");

        builder.setMessage("[정답] " + questions.get(question_displayNumber - 1).answerNum + "\n[해설]\n" + questions.get(question_displayNumber - 1).comment);        // 메세지 설정
        builder.setCancelable(false);        // 뒤로 버튼 클릭시 취소 가능 설정
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            // 확인 버튼 클릭시 설정
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();

            }
        });
        AlertDialog dialog = builder.create();    // 알림창 객체 생성
        dialog.show();    // 알림창 띄우기
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button_call:
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:010-0000-0000")));
                finish();
                break;

            case R.id.button_camera:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(takePictureIntent);
                finish();
                break;

            case R.id.button_message:
                Uri uri = Uri.parse("smsto:");
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", "The SMS text");
                startActivity(it);
                finish();
                break;

            case R.id.radioButton:
                if (questions.get(question_displayNumber - 1).answerNum == 1) {
                    explainDialog(1);
                } else {
                    mDBHelper.changeDb(question_displayNumber);
                    explainDialog(0);
                }
                break;

            case R.id.radioButton2:
                if (questions.get(question_displayNumber - 1).answerNum == 2) {
                    explainDialog(1);
                } else {
                    mDBHelper.changeDb(question_displayNumber);
                    explainDialog(0);
                }
                break;

            case R.id.radioButton3:
                if (questions.get(question_displayNumber - 1).answerNum == 3) {
                    explainDialog(1);
                } else {
                    mDBHelper.changeDb(question_displayNumber);
                    explainDialog(0);
                }
                break;

            case R.id.radioButton4:
                if (questions.get(question_displayNumber - 1).answerNum == 4) {
                    explainDialog(1);
                } else {
                    mDBHelper.changeDb(question_displayNumber);
                    explainDialog(0);
                }
                break;

        }
    }

    public void addTable() {
        //1
        mDBHelper.addQuestion(new Question("근대 산업 사회에서 일어난 변화에 해당하지 않는 것은?",
                "장인과 길드 체제", "산업 혁명 발생", "대량 생산 체제 구축", "증기 기관 발명", 4,
                "길드라는 동업 조합을 통해 이익을 극대화 한 시기는 중세 산업 사회이다. 근대 산업 사회에는 18세기 중반 영국에서 일어난 산업 혁명으로 인해 제 2차 산업의 비중이 확대되고 공장 제도가 확립되었다. 또한 공장제 기계 공업으로 대량 생산 체제가 구축되고 새로운 공업 도시가 형성되었다."));
        mDBHelper.addQuestion(new Question("다음 중 중화학 공업과 특징으로 알맞게 짝지어지지 않은 것은?",
                "자동차 공업 - 기술 집약적 종합 조립 공업", "조선 공업 - 주문자 생산 체제 위주", "화학공업 - 자본 집약적장치 공업", "철강공업 - 에너지 저소비형 친환경 공업", 4,
                "철강 공업의 주요 특징으로는 자동차, 조선, 기계, 건설 산업 등에 기초 소재를 공급하는 국가 기간 산업이고, 거대한 시설 투자가 필요한 장치 공업이며, 자본 집약적 산업이라는 것 등이 있다. \n소재 산업의 특성상 전·후방 산업과의 연관 효과가 매우 크고, 고에너지 사용 산업이자, 환경 오염물질 유발 산업이다."));
        mDBHelper.addQuestion(new Question("석유 화학 공업에 대한 설명으로 옳지 않은 것은?", "천연 섬유나 천연 고무를 대체할 수 있는 산업", "기술 축적을 전제로 기술 개발이 가능한 공업", "특정 주문자에 의해 발생하는 단품 수주 산업", "제품 부가 가치가 높아 개발 성공시 높은 이익 창출", 3,
                "석유 화학 공업은 기초 소재 산업으로, 특정 주문자에 생산 활동이 발생하는 단품 수주 산업은 아니다. \n석유 화학 공업의 특성으로 기초 소재 산업이고 자본 집약적 장치 산업이라는 점과 원료 및 기술의 해외 의존도가 높아 원료확보가 중대한 과제이고 고부가 가치 창출 산업임을 기억해야한다."));
        mDBHelper.addQuestion(new Question("인공위성 부품, 인공 심장 밸브제작에 사용되는 특수 합금은?", "파인 세라믹스", "초전도체", "형상 기억 합금", "광섬유", 3, "문제는 형상 기억 합금에 대한 내용이다. 형상 기억 합금은 원래의 형태를 기억하고 있다가 일정한 온도 이상으로 가열하면 이전의 모습으로 돌아가는 특수 합금으로 인공위성 부품, 인공 심장 밸브제작에 쓰인다. "
                + "초전도체는 절대 온도 0[K]에 가까운 극저온이 되면 전기 저항이 0이 되는 초전도 현상이 생기게 하는 재료로, 자기 부상열차, 고에너지 가속기에 쓰인다. \n파인 세라믹스는 정교한 세라믹이라는 뜻으로 원자로 부품, 인공 뼈, 절삭 공구에 쓰인다. \n광섬유는 중심부 유리를 통과하는 빛이 전반사가 일어나도록 한 광학적섬유로, 정보송신이 빠른 광케이블에 사용된다. "
                + "\n\n신소재 기술 KEY POINT : 형상 기억합금, 초전도체, 파인 세라믹스, 광섬유에서 자주 출제!"));
        mDBHelper.addQuestion(new Question("생산 현장의 실시간 모니터링으로 설비 관리, 작업내역 관리를 실행하여 생산성을 향상시키는 생산 정보 시스템은?", "생산지점 정보 시스템(POP)", "제조 실행 시스템(MES)", "컴퓨터 수치 제어 시스템(CNC)", "적시 생산 시스템(JIT)", 2,
                "제조 실행 시스템(MES)은 생산 현장의 실시간 모니터링, 불량 관리, 물류 및 작업내역 관리 등을 실행할 수 있는 시스템으로 자재 투입 관리, 인력 관리 등 생산 현장에서 발생할 수 있는 모든 정보를 통합 관리한다. \n생산 지점 정보 시스템(POP)은 관련 정보를 실시간으로 분석하여 생산 계획 및 다른 시스템에 제공하여 생산 관리의 효율화를 이루기 위해기초 데이터를 공급하는 역할을 한다."
                        + "\n컴퓨터 수치제어(CNC)는 컴퓨터를 이용하여 기계 가공을 자동으로 하는데 사용된다. \n적시 생산 시스템(JIT)은 생산에 필요한 부품을 필요한 때에 필요한 양만큼 공급하는 방법이다. "
                        + "이를 통해 부품의 재고량과 재고 비용을 줄이며 효율적으로 관리할 수있다."));
        mDBHelper.addQuestion(new Question("조명, 소음, 진동, 기압, 온열, 유해 광선을 통해 발생하는 유해요인은?", "물리적 요인", "화학적 요인", "생물학적 요인", "인간공학적 요인", 1,
                "에너지 형태로 인체에 전달되는 유해 요인은 물리적 요인이다. \n유해인자로는 조명, 소음, 진동, 기압, 온열, 유해 광선을 통해 발생된다. 화학적 요인은 물질 형태로 인체에 침입하고 유해인자로는 기체, 액체, 고체가 있다. "
                        + "\n생물학적요인은 생물체 형태로 인체에 전달되고 유해인자로는 바이러스, 세균, 곰팡이, 독소가 있다. \n인간 공학적요인은 작업 자세, 작업량, 공구나 기구, 중량물 취급 등으로 인해 발생하며, 유해 인자로는 과다한 작업, 단순 반복, 자세, 중량물이 있다. \n사회 심리적 요인은 작업과 관련된 정신적인 부담에 의해 발생한다. 유해인자로는 과로와 스트레스가 있다."));
        mDBHelper.addQuestion(new Question("활동 단계별 일정 계획을 수립하여 완료 시점을 예측할 수 있는 진행 관리에 한 방법은?", "퍼트", "브레인 스토밍", "갠트 차트", "체크리스트", 3,
                "갠트 차트나 퍼트는 자주 출제되고 있는 요소중 하나이다. \n퍼트는 전체적인 작업 일정을 세분화하여 진척 상황을 통제하는 것이다. \n갠트차트는 작업 진도를 관리 및 통제 하는 관리 도표이다. \n브레인 스토밍은 주제 선정시 활용하는 아이디어 산출기법이다. \n체크 리스트는 업무 결과를 점검하기 위하여 작성하는 서식으로 사후관리에 쓰인다."));
        mDBHelper.addQuestion(new Question("중대 재해에 대한 설명으로 옳지 않은 것은?", "사망자가 1인 이상 발생", "3개월 이상 요양하는 부상자가 동시에 2인 이상 발생", "부상자 또는 직업성 질병자가 동시에 10인 이상 발생", "재산 피해 발생 ", 4,
                "중대 재해는 사망자가 1인 이상 발생하거나, 3개월 이상의 요양을 요하는 부상자가 동시에 2인 이상 발생할시 또는 부상자 또는 직업성 질병자가 동시에 10인 이상 발생했을 시를 말한다. "
                        + "재산 피해 발생 정도는 중대 재해와 관련이 없다. \n\nKEY POINT : 사망자 1인 이상, 중상자 2인 이상, 부상자 혹은 질병인 10인이상 발생시 중대재해"));
        mDBHelper.addQuestion(new Question("노동 3권이 아닌 것은?", "단결권", "노동 기본권", "단체 교섭권", "단체 행동권", 2,
                "노동 기본권은 노동권과 노동 3권을 포함하는 개념이다. \n노동 3권은 노동 소외 현상을 막고 노동의 인간화를 위하여 헌법에서 보장하고 있는 권리로 단결권, 단체 교섭권, 단체 행동권이 있다. "
                        + " \n단결권은 근로자들이 근로 조건의 유지·개선을 목적으로 사용자와 대등한 교섭을 가지기 위한 단체를 구성하고 그 조직의 존립을 보장받기 위한 권리이다. \n단체 교섭권은 근로조건의 유지·개선을 이루어 내는 수단이자, "
                        + "단체 협약을 체결하기 위하여 노동조합이 사용자에 대하여 교섭을 요구할 수 있는 권리이다. \n단체 행동권은 근로자가 노동 조건의 유지·개선을 위하여 사용자에 대항하여 단체 행동을 할 수 있는 권리이다."
                        + "\n\nKEY POINT : 노동 3권은 모두 ‘단’자로 시작한다."));
        mDBHelper.addQuestion(new Question("근로자 유형별 법정 기준 근로 시간이 초과된 경우는?", "성인근로자 - 1주 45시간", "성인근로자 - 1일 8시간", "연소근로자 - 1주 40시간", "연소근로자 - 1일 7시간", 1,
                "성인근로자와 연소근로자의 1주 법정 기준 근로시간은 40시간으로 동일하다. 법정 기준 근로 시간에서 성인근로자는 1주 40(44)시간, 1일 8시간이다. 단, 괄호 안 숫자는 근로 기준법 예외 적용 사업장 적용 시간이다. 연소 근로자(15세이상~18세미만)는 1주 40시간, 1일 7시간이 법정 기준 근로시간이다."));
        //11
        mDBHelper.addQuestion(new Question("2016년 최저 임금은?", "5,580원", "6 30원", "6,530원", "7 80원", 2,
                "5,580원은 2015년 최저 임금이다. 2016년은 전년도 보다 인상된 금액인 6 30원으로 최저임금이 책정되었다."));
        mDBHelper.addQuestion(new Question("산업재산권의 종류와 권리유지기간에 대한 설명이 알맞지 않은 것은?", "특허권 - 출원일로부터 15년간", "실용신안권 - 출원일로부터 10년간", "상표권 - 설정 등록일부터 10년간", "디자인권 -설정 등록일부터20년간", 1,
                "특허권은 지금까지 없었던 물건이나 방법을 최초로 발명한 경우에 주어지는 권리로 리의 유지는 출원일로부터 20년간이다. \n실용신안권은 출원일로부터 10년, 상표권은 설정 등록일로부터 10년이며 갱신하여 사용 가능하고 디자인권은 설정 등록일부터 20년동안 권리가 유지된다."));
        mDBHelper.addQuestion(new Question("휘발유 증발 가스에 불이 붙어 자동차가 전소된 화재는 어느 분류에 해당하는가?", "A급 화재", "B급 화재", "C급 화재", "D급 화재", 2,
                "화재의 유형에는 일반 화재(A급 화재), 유류 화재(B급 화재), 전기화재(C급 화재), 금속 화재(D급 화재)가 있다. \n‘휘발유 증발 가스에 불이 붙어’는 유류 화재인 B급 화재이므로 정답은 2번이다."));
        mDBHelper.addQuestion(new Question("국내최초신기술 혹은 개선된 신기술이 적용된 신제품에 대해 정부가 인증하는 것은?", "신제품 인증(NEP)", "신기술 인증(NET)", "우수 재활용 제품 인증(GR)", "서비스 품질 우수 기업 인증", 1,
                "신기술이 적용된 제품을 평가하여 정부가 인증하는 것은 신제품 인증(NEP)이다. 제품과 기술이라는 단어에 유의해 NET와 NEP를 구별하도록 한다. \n신기술 인증(NET)는 신기술을 조기에 발굴하여 기술에 대한 우수성을 인증하는 것이다. "
                        + "\n우수 재활용 제품인증은 국내에서 개발·생산된 재활용 제품을 철저히 시험·분석·평가한 후 우수 제품에 대하여 품질을 평가하여 인증하는 제도이다. "
                        + "\n서비스 품질 우수 기업 인증은 서비스업을 대상으로 서비스 품질 우수 기업 인증을 신청한 기업(사업장) 또는 기관에 대하여 정부가 인증서를 수여하고 우수 업체로 널리 공표하는 제도이다."));
        mDBHelper.addQuestion(new Question("다음 중 영구적으로 보존하고 전달할 수 있는 장점을 가지고 있는 산업을 고르시오.", "섬유공업", "식품공업", "인쇄공업", "신발공업", 3,
                "인쇄 공업의 특징 \n-지식과 정보를 신속하고 값싸게 전달하고 보존하는 역할을 한다. \n-영구적으로 보존하고 전달할 수 있는 장점을 가지고 있다. \n-대량 복제가 가능하며, 전달 범위가 넓다."));
        mDBHelper.addQuestion(new Question("다음 중 건설 공업과 관련이 없는 것을 고르시오.", "국가 경제 발전에 미치는 파급 효과가 큰 산업이다.", "다품종 소량 주문 생산 위주의 생산구조를 가진다.", "형태나 내용면에서 복합적인 종합 산업이다.", "제조업과 같은 흐름 생산이 어렵다.", 2,
                "다품종 소량 주문 생산 위주의 생산구조를 가지는 것은 전기 공업으로 건설 공업은 특정한 발주자에 의해서 생산 활동이 전개되는 단품 수주 산업이다. \n\n*4번 추가 설명 건설 공업은 하나의 프로젝트가 서로 분리되어 진행되기 때문에 흐름 생산이 어렵다."));
        mDBHelper.addQuestion(new Question("다음 중 첨단 공업의 특징으로 알맞은 것을 고르시오.", "에너지 과소비형 공업", "제품의 수명 주기가 긴 공업", "국제 경쟁력이 치열한 공업", "연구 비용이 적게 소요되는 공업", 3,
                "첨단 공업의 특징 \n-에너지 절약형 \n–부가 가치가 매우 높음 \n–연구 비용이 많이 소요됨 \n–제품의 수명 주기가 짧다."));
        mDBHelper.addQuestion(new Question("주문 생산과 조립 생산의 중간에 위치한 방식으로 공작 기계, 가구 등이 대표적인 생산품인 생산 방식은?", "묶음 생산 방식", "개별 생산 방식", "연속 생산 방식", "프로젝트 생산 방식", 1,
                "개별 생산 방식은 소비자의 요구에 맞는 제품을 생산하며 제조 공정에서 융통성이 요구된다. 맞춤의류, 선박, 항공기 등이 대표적인 생산품이다. \n연속 생산 방식은 표준 제품을 대량으로 생산하는데 적합하고 시멘트, 비료 등이 대표적이다. "
                        + "\n프로젝트 생산 방식은 제한된 기간 내에 반복적이지 않은 생산품을 생산하며 1회 대규모 공사의 댐, 교량 건설 등이 대표적이다."));
        mDBHelper.addQuestion(new Question("계획 생산 방식의 특징으로 적절하지 않은 것을 고르시오.", "생산 설비는 전용 설비를 사용한다.", "납기 관리, 적정 인원 관리에 중점을 둔다.", "소품종 대량 생산에 적절하다.", "생산 수량이 많고 낮은 생산 원가인 경우에 적용된다.", 2,
                "2번은 주문 생산 방식의 특징으로 계획 생산 방식은 수요 예측, 재고 관리에 중점을 둔다."));
        mDBHelper.addQuestion(new Question("다음 중 표준의 분류가 다른 것을 하나 고르시오.", "KS", "JIS", "ABS", "NF", 3,
                " KS, JIS, NF는 각각 한국, 일본, 프랑스의 국가 표준이고 ABS는 미국 선급 협회의 기호로 단체 표준에 속한다."));
        //21
        mDBHelper.addQuestion(new Question("다음 중 품질 분임조와 관련이 없는 내용을 고르시오.", "전사적 혁신 활동으로 품질 및 생산성을 향상시킴", "생산 직원부터 경영자까지 모두 참여하여 직장의 사기를 높임", "전사적 품질 경영의 일환으로 직장에서의 중심적 역할을 수행함", "강제적으로 그룹 활동에 참가해야 함.", 4,
                "품질 분임조 활동의 마음가짐으로 ‘자주적으로 그룹 활동 및 전원 참가’가 있는 데 이는 4번과 정반대의 내용이다."));
        mDBHelper.addQuestion(new Question("제품 수요 변동에 대응하기 위한 재고로 불확실한 고객의 수요 변화에 능동적으로 대처하기 위한 재고는?", "안전 재고", "완충 재고", "예상 재고", "수송 재고", 1,
                "재고의 유형을 올바로 이해하고 있는지에 대해 묻는 문제이다. \n완충 재고 : 생산 현장에서 공정 단계별로 어느 정도 재고를 보유함으로써 산출량을 일정하게 유지하기 위한 재고. "
                        + "\n예상 재고(=계절 재고) : 생산 능력이 고정적이어서 계절 수요 등을 예측하여 고려한 재고로 의류, 농산물 등에서 주로 발생한다. "
                        + "\n수송 재고 : 이동 재고라고도 하며 이동 중에 있는 물품에 대한 재고."));
        mDBHelper.addQuestion(new Question("설비 관리에서 제품별 배치의 특징으로 옳지 않은 것을 고르시오.", "대량 생산으로 단위당 생산 원가를 낮출 수 있다.", "작업이 단순하다.", "설비와 투자비가 적게 든다.", "수요 변화에 대한 유연성이 떨어진다.", 3,
                "설비와 투자비가 적게 드는 것은 공정별 배치로 제품별 배치는 설비와 투자비가 많이 든다. \n1, 2 번은 제품별 배치의 장점이고 4번은 제품별 배치의 단점이다."));
        mDBHelper.addQuestion(new Question("품질 관리를 위해 생산 및 출하 단계의 제품을 몇 개씩 뽑아내어 검사 기준값에 적합한 지 여부를 판정하는 방법은?", "관리도법", "도수분포도법", "전수 검사", "샘플링 검사법", 4,
                "관리도법 : 관리도에 의하여 공정이 안정한 상태에 있는 지를 감시하여 공정을 안정한 상태로 유지하기 위하여 취하는 조치 방법으로 관리 상한선과 하한선을 정하여 공정의 이상 유무를 조기에 발견하기 위해 사용한다. "
                        + "\n도수분포도법 : 수집한 자료를 도표로 정리하는 방법  \n산포도법 : 결과와 원인의 관계를 파악하여 이 관계를 시각적으로 표현하고자 할 때 쓰이는 방법  \n전수 검사 : 물품 전체를 하나하나 전부 검사하는 방법으로 완벽함을 추구하는 경우에 사용한다."));
        mDBHelper.addQuestion(new Question("명령 지휘 계통이 단순하고 구성원의 권한과 책임이 명확한 경영 조직의 형태를 고르시오.", "기능식 조직", "프로젝트 조직", "사업부제 조직", "직계식 조직", 4,
                "직계식 조직의 특징 \n-경영자의 권한과 명령이 최하위층에 수직적, 단계적으로 전달되는 조직 형태이다. \n-통제나 조정이 용이하고 소규모의 사업에 적합하다. \n-횡적 연결이 잘 되지 않아 부서 간 유기적인 조정이 어렵다."));
        mDBHelper.addQuestion(new Question("다음 중 임금의 종류가 다른 것을 하나 고르시오.", "가족 수당", "지역 수당", "특수 근무급", "장려금", 3,
                "가족 수당(부양 가족 생활비 보조), 지역 수당(근무지 수당, 한랭 지역), 장려금(능력급제에서의 할증)은 기준 임금 즉 일반적인 노동 시간 및 작업 조건 하에서 수행되는 노동에 대한 임금이고 특수 근무급은 기준 외 임금, 소정의 일반 노동 시간 및 작업 조건 이외의 노동에 대해 지급되는 임금이다."));
        mDBHelper.addQuestion(new Question("다음 중 복리 후생의 종류가 다른 것을 하나 고르시오.", "국민 건강 보험료", "사원 여행", "국민 건강 보험료", "노인 장기요양 보험료", 2,
                "법정 복리 후생비에는 국민 건강 보험료, 국민 건강 보험료, 노인 장기요양 보험료, 산재 보험료, 고용 보험료가 있고, 법정 외 복리 후생비는 사원 여행, 휴양소 운영, 주택 자금 대출과 같은 각종 지원 형태이다."));
        mDBHelper.addQuestion(new Question("2인 이상의 무한 책임 사원과 유한 책임 사원으로 구성되는 형태의 기업을 고르시오.", "합명 회사", "합자 회사", "유한 회사", "주식 회사", 2,
                "합명 회사 – 2 인 이상의 무한 책임 사원만으로 조직되는 회사 \n유한 회사 – 2~50인의 유한 책임 사원으로만 구성 \n주식 회사 – 주식을 발행하여 설립하며 유한 책임 사원으로만 구성"));
        mDBHelper.addQuestion(new Question("2 인 이상의 무한 책임 사원만으로 조직되는 형태의 기업으로 고르시오.", "합명 회사", "주식회사", "합자회사", "유한회사", 1,
                "합자 회사 – 2인 이상의 무한 책임 사원과 유한 책임 사원으로 구성됨.  \n유한 회사 – 2~50인의 유한 책임 사원으로만 구성 \n주식 회사 – 주식을 발행하여 설립하며 유한 책임 사원으로만 구성"));
        mDBHelper.addQuestion(new Question("다음 중 창업의 범위에 속하는 것을 고르시오.", "타인의 기업을 승계하여 승계 전 사업과 동종의 사업을 하는 경우", "법인 간 기업 형태를 변경하여 동종의 사업을 하는 경우", "폐업 후 개시한 사업이 폐업 전과 동종의 사업인 경우", "아이디어의 우수성과 독창성을 바탕으로 하는 사업을 개시하는 경우", 4,
                "4번은 벤처 창업에 관한 설명으로 창업의 종류에는 일반 창업, 벤처 창업, 소호(SOHO) 창업이 있다. 1,2,3번은 창업 범위에서 제외되는 설명이다"));
        //31
        mDBHelper.addQuestion(new Question("자기 부상 열차, 고에너지 가속기 등에 사용되는 신소재는?", "파인 세라믹스", "초전도체", "광섬유", "결정화 유리", 2,
                "초전도체는 절대 온도 0[K]에 가까운 극저온이 되면 전기 저항이 0이 되는 초전도 현상이 생기게 하는 재료로, 자기 부상열차, 고에너지 가속기에 쓰인다."));
        mDBHelper.addQuestion(new Question("인공 뼈, 절삭 공구 제작에 사용되는 신소재는?", "파인 세라믹스", "초전도체", "광섬유", "결정화 유리", 1,
                "파인 세라믹스는 정교한 세라믹이라는 뜻으로 원자로 부품, 인공 뼈, 절삭 공구에 쓰인다. "));
        mDBHelper.addQuestion(new Question("재료의 회전과 공구의 선 운동에 의해 원통형 공작물을 가공하는 기계는?", "밀링 머신", "드릴링 머신", "선반", "압연기", 3,
                "재료의 회전과 공구의 선 운동에 의해 원통형 공작물을 가공하는 기계를 선반이다. "));
        mDBHelper.addQuestion(new Question("회전하는 공구에 재료를 이송하여 평면, 곡면 등을 가공하는 기계는?", "밀링 머신", "드릴링 머신", "선반", "압연기", 1,
                "회전하는 공구에 재료를 이송하여, 평면, 곡면 등을 가공하는 기계를 밀링 머신이라고 한다."));
        mDBHelper.addQuestion(new Question("1회성의 대규모 댐 공사, 교량 및 고속도로 건설 등이 대표적인 생산 방식은?", "개별 생산 방식", "연속 생산 방식", "묶음 생산 방식", "프로젝트 생산 방식", 4,
                "프로젝트 생산 방식은 제한된 기간 내에 반복적이지 않은 생산품을 생산하는 방식으로 기업의 경험, 기술력, 노하우는 특히 이런 생산방식에 있어서 중요한 요소이다. 댐, 교량, 고속도로 건설 등이 대표적인 예이다."));
        mDBHelper.addQuestion(new Question("제품 변경이나 신제품 도입율이 낮을 경우에 사용하기 적합한 생산 방식은?", "개별 생산 방식", "연속 생산 방식", "묶음 생산 방식", "프로젝트 생산 방식", 2,
                "연속 생산 방식은 표준 제품을 대량으로 생산하는 데 적합하고, 제품 변경 및 신제품 도입율이 낮을 경우에 사용한다. 자동차 같은 조립라인 생산 방식이나 시멘트, 비료 등을 생산하는 연속 흐름 생산 방식이다."));
        mDBHelper.addQuestion(new Question("컨베이어 시트템에 관한 내용으로 옳지 않은 것은?", "작업 숙련이 편리", "인간 소외의  문제를 야기", "작업 공정의 작업 시간이 동일", "자동차 조립 라인에 사용", 3,
                "3번 보기는 택트 시스템에 관한 내용이다. \n컨베이어 시스템은 작업시간이 골고루 분배 되도록 조립 공정을 여러 단계로 나누고, 작업자는 고정된 자리에서 이동식 조립 라인 위의 부품을 차례대로 조립하여 다음 작업자에게 보내어 제품이 완성하도록 생산하는 방식이다."));
        mDBHelper.addQuestion(new Question("재고 품목의 중요도와 가치에 따라 그 등급을 다르게 분류하여 관리하는 시스템은?", "MRP", "OEM", "ABC", "ERP", 3,
                "ABC 시스템은 재고 품목의 중요도와 가치에 따라 그 등급을 다르게 분류하여 관리하는 기법이다."));
        mDBHelper.addQuestion(new Question("한국 표준 직업 분류(KSCO)는 어느 기구에 기초하여 제정하였을까요?", "표준 산업 기구", "국제 노동 기구", "세계 무역 기구", "국제 표준화 기구", 2,
                "직업 관련 통계 자료의 정확성 및 비교성을 확보하기 위하여 작성 된 것으로 국제 노동 기구(ILO)의 국제 표준 직업 분류(ISCO)에 기초하여 제정하였다."));
        mDBHelper.addQuestion(new Question("사명감과 도덕성이 필요하여, 관료주의적 권위 의식을 버리고 봉사하는 자세로 업무를 수행하는 직업은?", "경찰", "미용사", "제품 판매원", "농업인", 1,
                "미용사와 제품 판매원과 농업인은 사명감과 봉사하는 자세가 직업에 필요한 능력은 아니다. 따라서 정답은 경찰이다."));

        //41
        mDBHelper.addQuestion(new Question("다음중 홀랜드의 직업 분류가 아닌 것은?", "실제적 유형", "탐구적 유형", "윤리적 유형", "예술적 유형", 3,
                "홀랜드의 직업 분류는 총 6가지이다. 실제적, 탐구적, 예술적, 사회적, 기업가적, 관습적 유형이 있다. 윤리적 유형은 포함이 되지 않는다. 따라서 정답은 윤리적 유형이다."));
        mDBHelper.addQuestion(new Question("사회 보장 제도에서 4대 사회 보험이 아닌 것은?", "국민 연금", "고용 보험", "산업 재해 보상 보험", "요양 보험", 4,
                "4대 사회 보험은 국민연금, 국민 건강 보험, 고용 보험, 산업 재해 보상 보험이 있다. 요양 보험의 실제 명칭은 노인 장기 요양 보험으로써 4대 보험은 아니다."));
        mDBHelper.addQuestion(new Question("자신이 맡은 일이 하늘로부터 부여 받은 것이라고 여기는 직업 의식은?", "연대의식", "귀천의식", "소명의식", "평등의식", 3,
                "소명의식이란 자신의 맡은 일이 하늘로부터 부여 받은 것이라고 여기는 의식이다. 그러므로 답은 소명의식이다."));
        mDBHelper.addQuestion(new Question("다음 중 직업인이 갖추어야 할 윤리 의식이 아닌 것은?", "천직 의식", "직분 의식", "봉사 의식", "장인 정신", 3,
                "직업인이 갖추어야 할 윤리 의식은 천직의식, 직분 의식, 장인 정신, 전문 의식, 인내심과 책임감이 있다. 따라서 정답은 봉사 의식이다."));
        mDBHelper.addQuestion(new Question("기업의 사회적 책임 유형이 아닌 것을 고르시오.", "경제적 책임", "법률적 책임", "자선적 책임", "평등적 책임", 4,
                "기업의 사회적 책임 유형에는 경제적 책임, 법률적 책임, 윤리적 책임, 자선적 책임이 있다. 따라서 평등적 책임이 정답이다."));
        mDBHelper.addQuestion(new Question("작업자의 불안전한 행동으로 인해 부딪힘 사고가 발생한 경우, 옳지 않은 설명은?", "사고의 원인은 버드의 재해 이론 ‘직접 원인’에 해당한다.", "안전 의식의 부족은 불안전한 행동에 해당한다.", "안전장치의 기능 제거는 불안전한 행동에 해당한다.", "위험물 취급 부주의는 불안전한 행동에 해당한다.", 2,
                "작업장의 불안전한 행동은 사고의 원인은 버드의 재해 이론 중 ‘직접 원인’에 해당한다. 안전의식이 부족은 ‘간접 원인’이 그중 교육적 원인에 해당한다."));
        mDBHelper.addQuestion(new Question("작업장의 불안전한 상태로 인한 부딪힘 사고가 발생한 경우, 옳은 설명은?", "작업 환경의 결함은 불안전한 상태에 해당한다.", "생산 공정의 결함은 불안전한 상태에 해당한다.", "안전 보호 장치의 결힘은 불안전한 상태에 해당한다.", "건물, 기계 장치 설계 불량은 불안전한 상태에 해당한다.", 4,
                "작업장의 불안전한 상태는 ‘직접 원인’에 해당하며, 불안전한 상태의 사례로는, 작업 환경의 결함, 생산공정의 결함, 안전 보호 장치의 결함이 있다. 건물, 기계 장치 설계 불량은 ‘간접 원인’ 중 기술적 결함이다."));
        mDBHelper.addQuestion(new Question("고전압 전기 설비 주위에 접근 차단을 위해 설치한 방호 울타리에 대해 옳은 설명은?", "이 방호 장치는 격리형 방호 장치이다.", "이 방호 장치는 포집형 방호 장치이다.", "이 방호 장치는 감지형 방호 장치이다.", "이 방호 장치는 접근 반응형 방호 장치이다.", 1,
                "전기 설비 주위에 접근을 차단하기 위한 방호 울타리를 설치하는 것은 덮개나 안전망 등 의 장치로써 설비를 완전히 차단하는 격리형 방호 장치를 설치한 것으로 옳은 설명이다."));
        mDBHelper.addQuestion(new Question("전통 악기를 제작하고 복원하는 일 자체를 좋아하는 악기장의 직업관은?", "개인 중심적 직업관", "소명적 직업관", "과정 지향적 직업관", "결과 지향적 직업관", 3,
                "‘전통 악기를 제작하고 복원하는 일 자체가 좋다.’ 라는 문장 진술은 과정 지향적 직업관을 나타낸 것이다. \n과정 지향적 직업관은 일과 직업이 수행되는 올바는 과정 그 자체를 중시한다."));
        mDBHelper.addQuestion(new Question("자신이 하는 일이 하늘로부터 부여 받은 일이라고 생각하는 사람이 가진 직업관은?", "개인 중심적 직업관", "소명적 직업관", "과정 지향적 직업관", "결과 지향적 직업관", 2,
                "소명적 직업관은 자신이 맡은 일에 최선을 다하는 것이 하늘의 뜻과 인간의 본연적 인 도리라고 생각하는 직업관이다."));

        //51
        mDBHelper.addQuestion(new Question("벤처기업에 관한 설명으로 옳지 않은 것은?", "기업을 세울 수 있는 입지를 지원받을 수 있다.", "지적 재산 보증을 받을 수 있다.", "법인세, 재산세 감면, 일정 기간 취득세 면제 등을 받는다.", "새로 설립하는 창업 회사들은 모두 벤처 기업이다.", 4,
                "벤처 기업이란 첨단 기술이나 새로운 아이디어를 사업화하는 데 있어서 경영의 위험성은 매우 높지만 성공할 경우 상당한 수익을 기대할 수 있는 기업을 말한다. \n유흥 사치 업종이나 기타 서비스 업은 벤 제외 업종이며 일정 요건을 만족시켜야 벤처 창업을 할 수 있다."));
        mDBHelper.addQuestion(new Question(" 공장 전체의 업무를 통합하여 정보 시스템으로 일체화한 시스템은?", "FMS", "CIM", "CAD", "CNC", 2,
                "공장 전체의 업무를 통합하여 정보 시스템으로 일체화한 시스템은 CIM이다. CIM을 도입하면 신제품 개발 기간과 납품 기간을 단축하고 품질 향상, 제조 원가의 절감과 기계 밀 설비의 가동률이 향상되는 효과가 있다."));
        mDBHelper.addQuestion(new Question("작업의 계속성 및 동시 진행이 어려운 시스템은?", "컨베이어 시스템", "로봇", "택트", "유연 생산 시스템", 3,
                "택트는 작업의 계속성 및 동시 진행이 어려워 컨베이어 시스템에 비해 능률이 떨어진다. 각각의 작업은 제품을 정지한 상태에서 가공하고, 한 작업이 긑나면 모든 공정이 일제히 하나씩 이동하여 작업하는 방식이다."));
        mDBHelper.addQuestion(new Question("다음 중 옳지 않은 것은?", "석유 정제 공업은 원료의 대부분을 수입에 의존한다.", "자동차 공업은 기술 집약적인 종합 조립 공업이다.", "섬유 공업은 초기 시설 투자비가 큰 장치 공업이다.", "철강 공업은 호프만의 산업 분류에 따르면 생산재 산업이다.", 3,
                "섬유 공업은 경공업의 속하는 산업이다. 장치 공업은 어떤 제품을 생산하기 위하여 거대한 설비와 각종 장치를 필요로 하는 공업, 석유 정제, 석유 화학, 제철 공업이 대표적이다."));
        mDBHelper.addQuestion(new Question("텀블러의 형상과 모양에 대한 권리를 취득하였을 때, 이 권리는 무엇인가?", "디자인권", "실용신안권", "특허권", "상표권", 1,
                "물건의 형상, 모양, 색채 또는 이들을 결합한 것에 주어지는 권리는 디자인권이다."));
        mDBHelper.addQuestion(new Question("벤처 제외 업종이 아닌 것은?", "유흥 사치 업종과 관련한 숙박 및 음식점업", "부동산업 및 입대업", "오락업 및 문화업", "신기술을 이용한 사업", 4,
                "신기술을 이용하여 사업화하는 것은 벤처 창업의 요건중 하나이다. \n유흥 사치 업종이나 부동산업 및 임대업, 오락업 및 문화업은 벤처 제회 업종이다."));
        mDBHelper.addQuestion(new Question("OO브랜드에 대한 권리를  취득하였을 때, 이 권리의 특징으로 옳은것은?", "갱신을 통해 권리 존속 기간을 연장할 수 있다.", "권리의 유지는 설정 등록일로 부터 20년간이다.", "권리의 유지는 출원일로부터 20년간이다.", "이 권리는 정보 재산권이다.", 1,
                "이 권리는 상표권이다. 상표권은 제품을 식별하기 위한 상호, 이름, 마크 등에 주어지는 권리이며 갱신을 통해 권리 존속 기간을 연장할 수 있다. 권리의 유지는 설정 등록일로부터 10년이다."));
        mDBHelper.addQuestion(new Question("산업 안전사고의 원인 중 직접 원인이 아닌 것은?", "안전 장치의 기능 제거", "불안전한 자세 및 동작", "작업 환경의 결함", "안전 교육의 불충분", 4,
                "안전 교육의 불충분은 간접 원인 중 교육적 원인에 해당하는 것이다. 작업 환경이나 생산공정, 장치의 결함과 같은 불안전한 상태는 직접 원인이며, 사람이 불안전한 행동을 하는 것 또한 직접 원인이다."));
        mDBHelper.addQuestion(new Question("환경을 매개로 한 무역 장벽을 해소에 도움을 주는 인증규격은?", "ISO14000", "ISO9000", "ISO9001", "ISO22000", 1,
                "ISO 14000은 국제 환경 표준화 인증 규격이다. \n그 외에 ISO9000은 품질 관리 및 품질 보증에 관한 규격이며, ISO9001은 신제품 설계에서부터 판매 후 발생한 문제까지 책임을 지는 시스템이 갖추어져 있음을 국제적으로 인증한다는 의미이다."));
        mDBHelper.addQuestion(new Question("직계식 조직의 단점을 보완하여 관리자의 업무를 전문화한 조직은?", "직계식 조직", "기능식 조직", "프로젝트 조직", "사업부제 조직", 2,
                "기능식 조직(테일러 조직)은 직계식 조직의 단점을 보완하여 관리자의 업무를 조직화한 조직이다. 전문화의 원칙과 분업의 원칙이 적용된 조직 형태이다."));

        //61
        mDBHelper.addQuestion(new Question("특정 과제를 해결하기 위하여 일시적으로 형성되는 조직 형태는?", "직계식 조직", "기능식 조직", "프로젝트 조직", "사업부제 조직", 3,
                "프로젝트 조직은 특정 과제를 해결하기 위하여 일시적으로 형성되는 조직 형태이며, 인원 구성이 탄력적이며 기동성이 있고 책임과 평가가 명확하다."));
        mDBHelper.addQuestion(new Question("업무 시간 중에 실제 업무를 수행하면서 동시에 실시하는 교육은?", "OJT", "Off JT", "감수성 훈련", "액션 러닝", 1,
                "상급자가 하급자에게 업무 시간 중에 실제 업무를 수행하면서 동시에 실시하는 교육을 OJT(직무 현장 교육 훈련)라고 한다. 도제식 교육, 직무 오리엔테이션, 인턴십, 멘토링 등이 포함된다."));
        mDBHelper.addQuestion(new Question("기업 연수원 교육, 세미나 등으로 교육 훈련을 진행하는 것을 총칭하는 것은?", "OJT", "Off JT", "도제 훈련", "외부 위탁 교육", 2,
                "직무가 수행되는 장소를 벗어나 시공간적으로 격리된 상태에서 실시되는 교육을 Off JT라고 한다. 기업 연수원 교육, 외부 위탁 교육, 세미나, 학회 등이 그 예시이다."));
        mDBHelper.addQuestion(new Question("국제적으로 중요한 습지 보호를 위한 국제적 협약으로 옳은 것은?", "몬트리올 의정서", "교토 의정서", "바젤 협약", "람사르 협약", 4,
                "람사르 협약은 국제적으로 중요한 습지를 보호하기 위한 협약이다. \n\nKEY POINT \n몬트리올 - 오존층 \n교토 의정서 - 지구 온난화 \n람사르 협약 - 습지 \n바젤 - 유해폐기물"));
        mDBHelper.addQuestion(new Question("공개 채용의 절차와 비용을 절감하고 효율적으로 인력을 확보하기 위해 회사에서 필요할 때에 우수한 인재를 채용하는 방식은?", "그룹 공채", "상시 채용제", "추천제", "리크루트제", 3,
                "그룹 공채 : 그룹 단위로 신입 사원을 선발하는 방식\n상시 채용제 : 인력 채용의 전문성과 유연성을 확보할 수 있다는 점에서 많은 기업들이 입사 기회를 계속 부여하는 방식\n리크루트제 : 선배 사원이 출신 학교 후배를 추천, 채용하는 방법. 채용 기간 동안 학교에 머물며 후배들과 접촉하여 적합한 인재를 선발."));
        mDBHelper.addQuestion(new Question("한 면접관의 면접이 끝나면 다음 면접실로 이동하면서 면접을 보는 유형은?", "개별 면접", "단독 면접", "다차원 면접", "블라인드 면접", 2,
                "개별 면점 : 면접관이 지원자를 개별적으로 면접.\n다차원 면접 : 면접관이 함께 레저, 스포츠, 합숙 행사 등을 통한 면접 \n블라인드 면접 : 면접관은 지원자의 학연, 지연, 혈연 등을 알지 못한 상태에서 면접."));
        mDBHelper.addQuestion(new Question("경영 의사 결정 및 행동이 윤리와 일치하고 법과 윤리 기준 내의 이윤을 추구하는 경영방식은?", "초윤리 경영", "비윤리 경영", "일치 경영", "윤리 경영", 4,
                "초윤리 경영 : 경영 의사 결정 및 행동이 윤리와 독립되어 있고 법 기준 내의 이윤을 추구한다.\n비윤리 경영 : 경영 의사 결정 및 행동이 윤리와 대립하고 수단을 가리지 않고 이윤을 추구한다. \n일치 경영이라는 것은 없다."));
        mDBHelper.addQuestion(new Question("자신이 맡은 일이 하늘로부터 부여 받은 것이라고 여기는 직업 의식은?", "소명 의식", "책임 의식", "연대 의식", "귀천 의식", 1,
                "책임 의식 : 모든 결과는 나의 선택으로 인해 일어나는 것이라고 여기는 의식 \n연대 의식 : 직업에 종사하는 구성원이 상호 간에 믿음으로 서로 의존하는 의식\n귀천 의식 : 직업을 종류에 따라 귀함과 천함이 있는 것으로 구분하는 의식"));
        mDBHelper.addQuestion(new Question("취업 후 4년차에 대학을 다니기 위해 회사에서 필요로 하는 정해진 시간에만 근무를 하는 경우의 근로 형태는?", "시간제 근무", "재택근무제", "안식년제", "파견 근로제", 1,
                "재택근무제는 회사 근무를 집에서도 할 수 있도록 하는 제도이다.\n안식년제는 근로 의욕을 활성화 시키기 위하여 몇 년에 한 번 정도 자기 계발을 위해 시간을 부여하는 제도이다.\n파견 근로제는 인력 파견 업체에 소속되어 요청하는 기업에 일정 기간 고용되는 형태이다."));
        mDBHelper.addQuestion(new Question("유해 폐기물의 국가 간 이동, 처리 사전 보고 의무화와 관련된 협약은?", "기후 변화 협약", "몬트리올 의정서", "교토 의정서", "바젤 협약", 4,
                "기후 변화 협약은 지구 온난화 방지를 위한 온실 효과를 일으키는 기체 발생 규제와 관련된 협약이다.\n몬트리올 의정서는 오존층 파괴로 인한 인체, 농작물 생태계 위협 및 피해와 관련된 협약이다.\n교토 의정서는 지구 온난화 방지를 이한 구체적 이행과 국가 별 온실가스 배출량의 엄격한 규제와 관련된 협약이다."));

        //71
        mDBHelper.addQuestion(new Question("다음 중 해양 에너지의 단점이 아닌 것은?", "고가의 설비 비용", "소비자와의 거리가 멀다.", "해양 생태계에 영향을 줄 수 있다.", "열에너지만을 생성하므로 성장에 한계 존재", 4,
                "열에너지만을 생성하여 성장에 한계가 존재하는 것은 태양열 에너지의 단점이다."));
        mDBHelper.addQuestion(new Question("다음 중 중대재해에 해당하지 않는 피해 현황은?", "근로자 2명 사망", "근로자 4명 부상(1개월 요양)", "근로자 7명 부상(15주 이상 치료)", "근로자 17명 부상", 2,
                "중대 재해에 해당하는 경우 \n- 사망자가 1인 이상 발생 \n- 3개월 이상의 요양을 요하는 부상자가 동시에 2인 이상 발생 \n- 부상자 또는 직업성 질병자가 동시에 10인 이상 발생"));
        mDBHelper.addQuestion(new Question("다음중 3정 5S에서 3정에 해당하는 내용이 아닌 것은?", "정품", "정량", "정리", "정위치", 3,
                "생산 혁신의 근간이 되는 기본 활동인 3정5S에서 3정은 정품, 정량, 정위치가 있다. 정리는 5S에 해당된다."));
        mDBHelper.addQuestion(new Question("6시그마의 효과가 아닌 것은?", "PROCESS", "PRODUCT", "PEOPLE", "PROFIT", 4,
                "6시그마 효과에는 PROCESS(불량 감소, 낭비 요소 제거 등), PRODUCT(상품,서비스 품질 향상 등), PEOPLE(혁신 인재 육성 등)이 있다."));
        mDBHelper.addQuestion(new Question("ERP의 성공 요소가 아닌 것을 고르시오.", "분명한 목표 설정", "적합한 분임조 구성", "프로젝트 진행 비공개화", "지속적인 사후 관리", 3,
                "ERP의 성공 요소에는 분명한 목표 설정, 전 사원의 적극 참여, 정직한 내부 현황 파악, 적합한 분임조 구성, 프로젝트 진행 투명화, 충분한 사전 교육, 철저한 시스템 점검, 지속적인 사후 관리가 있다."));
        mDBHelper.addQuestion(new Question("생산 시스템의 활용에 대한 관리가 아닌 것을 고르시오.", "공급 사실 관리(SCM)", "고객 관계 관리(CRM)", "전사적 자원 관리(ERP)", "자재 사용 계획(MUP)", 4,
                "생산 시스템의 활용으로는 공급 사실 관리, 고객 관계 관리, 전사적 자원 관리가 있고 부가적으로 자재 소요 계획(MRP)가 있다."));
        mDBHelper.addQuestion(new Question("생산 혁신을 위한 생산 방식 중에서  ‘낭비 제거 경영’을 나타내는 것을?", "린시스템", "적시 생산 시스템", "6시그마", "3정 5S", 1,
                "린시스템은 자재 구매에서 생산, 재고 관리, 판매에 이르기까지 전 과정에서 손실을 최소화한다는 개념으로 군살 없는 경영을 위한 ‘낭비 제거 경영’을 나타낸다."));
        mDBHelper.addQuestion(new Question("품질 분임조 운영 절차 중 주제선정에서 쓰는 방법은?", "브레인스토밍", "특성 요인도", "막대그래프", "표준화", 1,
                "주제 선정 단계에서는 분임조가 해결할 과제를 선정하는 단계이다. 주요 방법으로는 브레인스토밍, 브레인라이팅이있다. "));
        mDBHelper.addQuestion(new Question("근로자가 업무 중 상해를 입었을 때, 고의가 아닐 시 그 손실을 보상해 주는 사회보험은?", "국민 연금", "산업 재해 보상 보험", "고용 보험", "국민 건강 보험", 2,
                "산업 재해 보상 보험은 근로자가 업무 중 부상, 질병, 사망 등의 피해가 생겼을 때, 고의로 안전 규정을 어기거나 스스로 상처를 입힌 경우가 아니라면 발생된 피해를 업무상 재해로 보아 그 손실을 봉사해 주기 위한 제도이다."));
        mDBHelper.addQuestion(new Question("실직자에게 실업 급여를 지급하여 생계를 지원하고 재취업을 촉진하는 사회보험은?", "국민 연금", "산업 재해 보상 보험", "고용 보험", "국민 건강 보험", 3,
                "고용 보험은 실직자에게 실업 급여를 지급하여 생계를 지원하고 재취업을 촉진하는 한편, 실업의 예방과 직업 훈련 강화를 위하여 기업에 장려금 등을 지원하는 제도이다."));

        //80문제







    }
}