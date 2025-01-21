package com.quotawish.leaveword;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class EnglishWordTester {

    public static final String DEMO_ENGLISH_JSON = "{\n" +
            "  \"id\": 101,\n" +
            "  \"wordHead\": \"Effective\",\n" +
            "  \"wordId\": \"word_101\",\n" +
            "  \"content\": {\n" +
            "    \"sentence\": {\n" +
            "      \"text\": \"He is an effective teacher.\",\n" +
            "      \"translation\": \"他是一位有效的教师。\"\n" +
            "    },\n" +
            "    \"usphone\": \"ɪˈfɛktɪv\",\n" +
            "    \"syno\": {\n" +
            "      \"items\": [\n" +
            "        {\n" +
            "          \"word\": \"efficient\",\n" +
            "          \"translation\": \"高效的\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"word\": \"productive\",\n" +
            "          \"translation\": \"有成效的\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    \"ukphone\": \"ɪˈfɛktɪv\",\n" +
            "    \"ukspeech\": \"http://example.com/speech/uk/effective.mp3\",\n" +
            "    \"phrase\": {\n" +
            "      \"items\": [\n" +
            "        {\n" +
            "          \"text\": \"effective communication\",\n" +
            "          \"translation\": \"有效的沟通\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"text\": \"effective solution\",\n" +
            "          \"translation\": \"有效的解决方案\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    \"relWord\": {\n" +
            "      \"items\": [\n" +
            "        {\n" +
            "          \"word\": \"ineffective\",\n" +
            "          \"translation\": \"无效的\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"word\": \"efficient\",\n" +
            "          \"translation\": \"高效的\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    \"trans\": [\n" +
            "      {\n" +
            "        \"language\": \"Spanish\",\n" +
            "        \"translation\": \"Efectivo\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"language\": \"French\",\n" +
            "        \"translation\": \"Efficace\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"antonyms\": [\n" +
            "      {\n" +
            "        \"word\": \"ineffective\",\n" +
            "        \"translation\": \"无效的\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"categorised\": [\n" +
            "      {\n" +
            "        \"category\": \"Adjective\",\n" +
            "        \"words\": [\n" +
            "          {\n" +
            "            \"word\": \"efficient\",\n" +
            "            \"translation\": \"高效的\"\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ],\n" +
            "    \"definition\": [\n" +
            "      \"Producing desired results without wasted effort or expense.\",\n" +
            "      \"Capable of producing the intended result.\"\n" +
            "    ],\n" +
            "    \"derived\": [\n" +
            "      {\n" +
            "        \"word\": \"effectiveness\",\n" +
            "        \"translation\": \"有效性\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"examples\": [\n" +
            "      {\n" +
            "        \"text\": \"He is an effective teacher.\",\n" +
            "        \"translation\": \"他是一位有效的教师。\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"text\": \"The new policy proved to be effective.\",\n" +
            "        \"translation\": \"新政策证明是有效的。\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"img\": [\n" +
            "      \"http://example.com/images/effective.jpg\"\n" +
            "    ],\n" +
            "    \"phonetic\": \"ɪˈfɛktɪv\",\n" +
            "    \"phrases\": [\n" +
            "      {\n" +
            "        \"text\": \"effective communication\",\n" +
            "        \"translation\": \"有效的沟通\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"prefix\": \"eff-\",\n" +
            "    \"remember\": \"Think of 'effect' in 'effectively'.\",\n" +
            "    \"story\": \"The word 'effective' comes from the Latin 'effectus', meaning 'result'.\",\n" +
            "    \"suffix\": \"-ive\",\n" +
            "    \"synonyms\": [\n" +
            "      {\n" +
            "        \"word\": \"efficient\",\n" +
            "        \"translation\": \"高效的\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"word\": \"productive\",\n" +
            "        \"translation\": \"有成效的\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"transform\": [\n" +
            "      {\n" +
            "        \"form\": \"effectiveness\",\n" +
            "        \"translation\": \"有效性\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"translation\": \"有效的\",\n" +
            "    \"type\": [\n" +
            "      \"Adjective\"\n" +
            "    ],\n" +
            "    \"backgroundStory\": \"The term 'effective' has been in use since the 15th century, derived from the Latin 'effectus'.\"\n" +
            "  },\n" +
            "  \"createTime\": \"2023-10-01T12:34:56Z\",\n" +
            "  \"updateTime\": \"2023-10-01T12:34:56Z\",\n" +
            "  \"isDelete\": 0\n" +
            "}\n";

    public static void main(String[] args) {
        // JSON PARSE
        JSONObject entries = JSONUtil.parseObj(DEMO_ENGLISH_JSON);

        // 输出
        System.out.println(entries);
        System.out.println(entries.toJSONString(2));

        // 匹配
        System.out.println(entries.getByPath("content.word.wordHead"));
    }
}
