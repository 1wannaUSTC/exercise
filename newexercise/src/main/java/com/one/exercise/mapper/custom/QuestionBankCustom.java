package com.one.exercise.mapper.custom;

import com.one.exercise.pojo.QuestionBank;
import com.one.exercise.utils.Tools;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.javassist.runtime.Desc;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class QuestionBankCustom {

    public String getQuestionList(@Param("startIndex") int startIndex,
                                  @Param("pageSize") int pageSize,
                                  @Param("questionBank") QuestionBank questionBank,
                                  @Param("errorRate") String errorRate) {
        return new SQL() {
            {
                StringBuilder sb = new StringBuilder("");
                SELECT("*");
                FROM("question_bank");

                // 判断教师id是否为空
                if (questionBank.getTeacherId() != null && questionBank.getTeacherId() != 0){
                    sb.append(" teacher_id=#{questionBank.teacherId} AND ");
                }

                // 判断公共或私有
                if (questionBank.getIsPublic() != null){
                    if (questionBank.getIsPublic()==1 || questionBank.getIsPublic() == 0)
                        sb.append(" is_public=#{questionBank.isPublic} AND  ");
                }

                // 判断学科
                if ( !Tools.isNullStr(questionBank.getSubject())){
                    sb.append(" subject=#{questionBank.subject} AND  ");
                }

                // 判断分数
                if (questionBank.getScore() != null && questionBank.getScore() !=  0){
                    sb.append(" score=#{questionBank.score} AND  ");
                }

                // 判断类型
                if ( !Tools.isNullStr(questionBank.getType())){
                    sb.append(" type=#{questionBank.type} AND  ");
                }

                // 判断难度
                if ( !Tools.isNullStr(questionBank.getDifficulty())){
                    sb.append(" difficulty=#{questionBank.difficulty} AND  ");
                }

                int lastAnd = sb.lastIndexOf("AND");
                String sqlWhere = " ";
                if (lastAnd != -1){
                    sqlWhere = sb.substring(0, lastAnd);
                }

                WHERE(sqlWhere);

                if (errorRate.trim().equalsIgnoreCase("DESC") || errorRate.trim().equalsIgnoreCase("asc")){
                    ORDER_BY(" error / (correct+error) " + errorRate + ", question_id DESC ");
                }else {
                    ORDER_BY(" question_id DESC ");
                }

                LIMIT("#{startIndex},#{pageSize}");
            }
        }.toString();
    }


    public String selectQuestionByIdList(@Param("ids") Set<Long> ids){
        return new SQL(){
            {
                // (1,2,33,51)
                SELECT("*");
                FROM("question_bank");

                Iterator<Long> iterator = ids.iterator();

                StringBuffer sb = new StringBuffer("(");
                while (iterator.hasNext()){
                    Long next = iterator.next();
                    sb.append(next + ",");
                }
                String s = sb.toString().substring(0, sb.toString().length()-1) + ")";
                WHERE("question_id in " + s);
            }
        }.toString();
    }

    public String selectQuestionByIdByTeacherIdList(@Param("ids") Set<Long> ids, @Param("teacherId") Integer teacherId){
        return new SQL(){
            {
                // (1,2,33,51)
                SELECT("*");
                FROM("question_bank");

                Iterator<Long> iterator = ids.iterator();

                StringBuffer sb = new StringBuffer("(");
                while (iterator.hasNext()){
                    Long next = iterator.next();
                    sb.append(next + ",");
                }
                String s = sb.toString().substring(0, sb.toString().length()-1) + ")";
                WHERE(" teacher_id =" + teacherId + " AND question_id in " + s);
            }
        }.toString();
    }

    public String selectLikeKey(@Param("searchKey") String searchKey,
                                @Param("id") long id){
        String key = "'%" +searchKey+ "%'";
        StringBuffer sb = new StringBuffer("");
        sb.append("SELECT DISTINCT qb.* FROM question_bank AS qb, teacher_subject as ts, subject as s ");
        sb.append(" WHERE qb.teacher_id=#{id} ");
        sb.append("AND (qb.question LIKE " + key + " OR qb.`option` LIKE "  + key +  " OR qb.`answer` LIKE "+ key +" OR ");
        sb.append("(qb.teacher_id = ts.teacher_id AND s.subject_id = ts.subject_id AND s.subject_name LIKE " + key + " ))");
        sb.append("LIMIT #{startIndex},#{pageSize}");
        return sb.toString();
    }

    public String selectLikeKey2(@Param("searchKey") String searchKey){
        String key = "'%" +searchKey+ "%'";
        StringBuffer sb = new StringBuffer("");
        sb.append("SELECT DISTINCT qb.* FROM question_bank AS qb, teacher_subject as ts, subject as s ");
        sb.append(" WHERE ");
        sb.append(" (qb.question LIKE " + key + " OR qb.`option` LIKE "  + key +  " OR qb.`answer` LIKE "+ key +" OR ");
        sb.append("(qb.teacher_id = ts.teacher_id AND s.subject_id = ts.subject_id AND s.subject_name LIKE " + key + " ))");
        sb.append("LIMIT #{startIndex},#{pageSize}");
        return sb.toString();
    }

    public String selectLikeKeyCount(@Param("searchKey") String searchKey,
                                @Param("id") long id){
        String key = "'%" +searchKey+ "%'";
        StringBuffer sb = new StringBuffer("");
        sb.append("SELECT DISTINCT COUNT(*) FROM question_bank AS qb, teacher_subject as ts, subject as s ");
        sb.append(" WHERE qb.teacher_id=#{id} ");
        sb.append("AND (qb.question LIKE " + key + " OR qb.`option` LIKE "  + key +  " OR qb.`answer` LIKE "+ key +" OR ");
        sb.append("(qb.teacher_id = ts.teacher_id AND s.subject_id = ts.subject_id AND s.subject_name LIKE " + key + " ))");
        sb.append("LIMIT 0,1000");
        return sb.toString();
    }

    public String selectLikeKeyCount2(@Param("searchKey") String searchKey){
        String key = "'%" +searchKey+ "%'";
        StringBuffer sb = new StringBuffer("");
        sb.append("SELECT DISTINCT COUNT(*) FROM question_bank AS qb, teacher_subject as ts, subject as s ");
        sb.append(" WHERE ");
        sb.append(" (qb.question LIKE " + key + " OR qb.`option` LIKE "  + key +  " OR qb.`answer` LIKE "+ key +" OR ");
        sb.append("(qb.teacher_id = ts.teacher_id AND s.subject_id = ts.subject_id AND s.subject_name LIKE " + key + " ))");
        sb.append("LIMIT 0,1000");
        return sb.toString();
    }

    public String updateMoreQuestion(@Param("questions") List<QuestionBank> questionBanks){

        /*
        UPDATE `exercise`.`question_bank`
        SET `teacher_id` = 15, `is_public` = 1, `major` = '阿斯2芬阿发', `subject_id` = 2, `subject` = '数据库2', `type` = '多选', `question` = '阿斯蒂芬阿12蒂芬', `option` = '12', `answer` = '发挂号费挂21费低功耗', `uuid_short` = '12', `score` = 6, `difficulty` = '一般', `validity` = 2 WHERE `question_id` = 76
         */
        StringBuilder sb = new StringBuilder();
        for (QuestionBank question : questionBanks) {
            if (question.getOption() == null){
                question.setOption("");
            }
            String s = new SQL(){
                {
                    UPDATE("question_bank");
                    SET("question = '" + question.getQuestion() + "'", "option = '"+question.getOption() + "'", "answer = '" + question.getAnswer() + "'");
                    WHERE("question_id = " + question.getQuestionId() + " AND teacher_id = " + question.getTeacherId());
                }
            }.toString() + ";\n";
            sb.append(s);
        }
        return sb.toString();
    }
}