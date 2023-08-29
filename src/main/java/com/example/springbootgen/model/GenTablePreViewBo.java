package com.example.springbootgen.model;

import java.io.Serializable;
import java.util.List;

/**
 * TODO
 *
 * @author WUBX
 * @date 2023/6/27 14:14
 */
public class GenTablePreViewBo implements Serializable {
    private static final long serialVersionUID = -6739701046681891412L;

    /** 后端代码结果集 */
    private List<GenTableCodeBO> genBasicCodeBackendList;

    public static class GenTableCodeBO {

        /**
         * 代码文件名称
         */
        private String codeFileName;

        /**
         * 代码文件带路径名称
         */
        private String codeFileWithPathName;

        /**
         * 代码文件内容
         */
        private String codeFileContent;

        public String getCodeFileName() {
            return codeFileName;
        }

        public void setCodeFileName(String codeFileName) {
            this.codeFileName = codeFileName;
        }

        public String getCodeFileWithPathName() {
            return codeFileWithPathName;
        }

        public void setCodeFileWithPathName(String codeFileWithPathName) {
            this.codeFileWithPathName = codeFileWithPathName;
        }

        public String getCodeFileContent() {
            return codeFileContent;
        }

        public void setCodeFileContent(String codeFileContent) {
            this.codeFileContent = codeFileContent;
        }
    }

    public List<GenTableCodeBO> getGenBasicCodeBackendList() {
        return genBasicCodeBackendList;
    }

    public void setGenBasicCodeBackendList(List<GenTableCodeBO> genBasicCodeBackendList) {
        this.genBasicCodeBackendList = genBasicCodeBackendList;
    }
}
