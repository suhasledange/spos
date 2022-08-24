public class TableRow {

        String sym;
        int addr,idx;

        public TableRow(String s,int a) {
            sym = s;
            addr = a;
            idx=0;
        }
        public TableRow(String s,int a,int i){
                sym = s;
                addr = a;
                idx = i;
        }

        public String getSym(){
            return sym;
        }
        public void setSym(String s){
            sym=s;
        }

        public int getAddr() {
            return addr;
        }
        public void setAddr(int a){
            addr = a;
        }

        public int getIdx() {
            return idx;
        }
        public void setIdx(int idx) {
            this.idx = idx;
        }

}
