package com.quotawish.leaveword;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.quotawish.leaveword.model.entity.english.word.WordContent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 主类测试
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Slf4j
@SpringBootTest
class MainApplicationTests {

    static String info = "{\n" +
            "    \"img\": [\n" +
            "        \"https://example.com/images/warrant_1.jpg\",\n" +
            "        \"https://example.com/images/warrant_2.png\"\n" +
            "    ],\n" +
            "    \"parts\": [\n" +
            "        {\n" +
            "            \"data\": {\n" +
            "                \"meaning\": \"The core part of the word with its basic meaning.\"\n" +
            "            },\n" +
            "            \"type\": \"ROOT\",\n" +
            "            \"content\": \"warrant\",\n" +
            "            \"description\": \"单词的基本部分\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"story\": \"In a legal case, a warrant is issued to allow the authorities to take certain actions.\",\n" +
            "    \"title\": \"warrant\",\n" +
            "    \"content\": \"A document that authorizes something or gives the right to do something.\",\n" +
            "    \"derived\": [\n" +
            "        {\n" +
            "            \"data\": {\n" +
            "                \"definition\": \"To give official permission or approval for something.\"\n" +
            "            },\n" +
            "            \"type\": \"SYNONYM\",\n" +
            "            \"content\": \"authorize\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"data\": {\n" +
            "                \"definition\": \"To forbid or prevent something from being done.\"\n" +
            "            },\n" +
            "            \"type\": \"ANTONYM\",\n" +
            "            \"content\": \"prohibit\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"data\": {\n" +
            "                \"definition\": \"The act of allowing someone to do something or the state of being allowed to do something.\"\n" +
            "            },\n" +
            "            \"type\": \"HYPERNYM\",\n" +
            "            \"content\": \"permission\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"data\": {\n" +
            "                \"definition\": \"A legal document that authorizes the police to search a particular place.\"\n" +
            "            },\n" +
            "            \"type\": \"HYPONYM\",\n" +
            "            \"content\": \"search warrant\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"data\": {\n" +
            "                \"definition\": \"To promise or ensure that something will happen or be the case.\"\n" +
            "            },\n" +
            "            \"type\": \"MEANING_CONFUSABLE\",\n" +
            "            \"content\": \"guarantee\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"remember\": \"Remember 'war rant' to recall the meaning of warrant.\",\n" +
            "    \"transform\": [\n" +
            "        {\n" +
            "            \"id\": 1,\n" +
            "            \"data\": {\n" +
            "                \"plural\": \"复数形式\"\n" +
            "            },\n" +
            "            \"type\": \"PLURAL\",\n" +
            "            \"content\": \"warrants\",\n" +
            "            \"example\": {\n" +
            "                \"type\": \"SENTENCE\",\n" +
            "                \"addon\": \"\",\n" +
            "                \"audio\": {\n" +
            "                    \"info\": {},\n" +
            "                    \"audio\": \"\",\n" +
            "                    \"content\": \"\",\n" +
            "                    \"description\": \"\"\n" +
            "                },\n" +
            "                \"sentence\": \"The court issued several warrants.\",\n" +
            "                \"highlight\": \"warrants\",\n" +
            "                \"translation\": \"法院发出了几份授权令。\"\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 2,\n" +
            "            \"data\": {\n" +
            "                \"present_participle\": \"现在分词形式\"\n" +
            "            },\n" +
            "            \"type\": \"ING_FORM\",\n" +
            "            \"content\": \"warranting\",\n" +
            "            \"example\": {\n" +
            "                \"type\": \"PHRASE\",\n" +
            "                \"addon\": \"\",\n" +
            "                \"audio\": {\n" +
            "                    \"info\": {},\n" +
            "                    \"audio\": \"\",\n" +
            "                    \"content\": \"\",\n" +
            "                    \"description\": \"\"\n" +
            "                },\n" +
            "                \"sentence\": \"The situation is warranting immediate action.\",\n" +
            "                \"highlight\": \"warranting\",\n" +
            "                \"translation\": \"这种情况需要立即采取行动。\"\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 3,\n" +
            "            \"data\": {\n" +
            "                \"past_tense\": \"过去式\"\n" +
            "            },\n" +
            "            \"type\": \"ED_FORM\",\n" +
            "            \"content\": \"warranted\",\n" +
            "            \"example\": {\n" +
            "                \"type\": \"SENTENCE\",\n" +
            "                \"addon\": \"\",\n" +
            "                \"audio\": {\n" +
            "                    \"info\": {},\n" +
            "                    \"audio\": \"\",\n" +
            "                    \"content\": \"\",\n" +
            "                    \"description\": \"\"\n" +
            "                },\n" +
            "                \"sentence\": \"His actions were warranted under the circumstances.\",\n" +
            "                \"highlight\": \"warranted\",\n" +
            "                \"translation\": \"在这种情况下，他的行为是有正当理由的。\"\n" +
            "            }\n" +
            "        }\n" +
            "    ],\n" +
            "    \"translation\": [\n" +
            "        {\n" +
            "            \"type\": \"NOUN\",\n" +
            "            \"audio\": {\n" +
            "                \"info\": {},\n" +
            "                \"audio\": \"https://example.com/audio/british_warrant.mp3\",\n" +
            "                \"content\": \"/ˈwɒrənt/\",\n" +
            "                \"description\": \"英式发音\"\n" +
            "            },\n" +
            "            \"example\": {\n" +
            "                \"type\": \"SENTENCE\",\n" +
            "                \"addon\": \"\",\n" +
            "                \"audio\": {\n" +
            "                    \"info\": {},\n" +
            "                    \"audio\": \"\",\n" +
            "                    \"content\": \"\",\n" +
            "                    \"description\": \"\"\n" +
            "                },\n" +
            "                \"sentence\": \"The police obtained a warrant to search the house.\",\n" +
            "                \"highlight\": \"warrant\",\n" +
            "                \"translation\": \"警察获得了搜查这所房子的授权令。\"\n" +
            "            },\n" +
            "            \"phonetic\": \"/ˈwɒrənt/\",\n" +
            "            \"typeText\": \"名词\",\n" +
            "            \"frequency\": 4,\n" +
            "            \"definition\": \"A document that gives the authority to do something.\",\n" +
            "            \"translation\": \"授权令\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"type\": \"VERB\",\n" +
            "            \"audio\": {\n" +
            "                \"info\": {},\n" +
            "                \"audio\": \"https://example.com/audio/american_warrant.mp3\",\n" +
            "                \"content\": \"/ˈwɔːrənt/\",\n" +
            "                \"description\": \"美式发音\"\n" +
            "            },\n" +
            "            \"example\": {\n" +
            "                \"type\": \"SENTENCE\",\n" +
            "                \"addon\": \"\",\n" +
            "                \"audio\": {\n" +
            "                    \"info\": {},\n" +
            "                    \"audio\": \"\",\n" +
            "                    \"content\": \"\",\n" +
            "                    \"description\": \"\"\n" +
            "                },\n" +
            "                \"sentence\": \"The circumstances warrant such a decision.\",\n" +
            "                \"highlight\": \"warrant\",\n" +
            "                \"translation\": \"这种情况使这样的决定成为必要。\"\n" +
            "            },\n" +
            "            \"phonetic\": \"/ˈwɔːrənt/\",\n" +
            "            \"typeText\": \"动词\",\n" +
            "            \"frequency\": 3,\n" +
            "            \"definition\": \"To make something necessary or justify it.\",\n" +
            "            \"translation\": \"使有必要\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"examplePhrases\": [\n" +
            "        {\n" +
            "            \"type\": \"SENTENCE\",\n" +
            "            \"addon\": \"\",\n" +
            "            \"audio\": {\n" +
            "                \"info\": {},\n" +
            "                \"audio\": \"\",\n" +
            "                \"content\": \"\",\n" +
            "                \"description\": \"\"\n" +
            "            },\n" +
            "            \"sentence\": \"A warrant is required for a police search.\",\n" +
            "            \"highlight\": \"warrant\",\n" +
            "            \"translation\": \"警察搜查需要授权令。\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"type\": \"PHRASE\",\n" +
            "            \"addon\": \"\",\n" +
            "            \"audio\": {\n" +
            "                \"info\": {},\n" +
            "                \"audio\": \"\",\n" +
            "                \"content\": \"\",\n" +
            "                \"description\": \"\"\n" +
            "            },\n" +
            "            \"sentence\": \"The judge issued a warrant for his arrest.\",\n" +
            "            \"highlight\": \"warrant for arrest\",\n" +
            "            \"translation\": \"法官发出了对他的逮捕令。\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"backgroundStory\": \"The word 'warrant' comes from the Old French 'warant', meaning 'defender, protector'. It has evolved over time to have the meanings it has today in the English language.\",\n" +
            "    \"britishPronounce\": {\n" +
            "        \"info\": {},\n" +
            "        \"audio\": \"https://dict.youdao.com/dictvoice?audio=warrant&type=1\",\n" +
            "        \"content\": \"/ˈwɒrənt/\",\n" +
            "        \"description\": \"英式发音\"\n" +
            "    },\n" +
            "    \"americanPronounce\": {\n" +
            "        \"info\": {},\n" +
            "        \"audio\": \"https://dict.youdao.com/dictvoice?audio=warrant&type=2\",\n" +
            "        \"content\": \"/ˈwɔːrənt/\",\n" +
            "        \"description\": \"美式发音\"\n" +
            "    }\n" +
            "}";

    @Test
    void contextLoads() {
        JSON json = JSONUtil.parse(info);

        WordContent bean = json.toBean(WordContent.class);

        log.warn(String.valueOf(JSONUtil.toJsonStr(bean).equalsIgnoreCase(info)));
    }

}
