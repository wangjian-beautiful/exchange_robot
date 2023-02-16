

package com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v2.pojo;

import java.util.List;

public class DepositAddressDto {
    private String coinName;
    private List<ChainAddress> chains;

    public DepositAddressDto() {
    }

    public String getCoinName() {
        return this.coinName;
    }

    public List<ChainAddress> getChains() {
        return this.chains;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public void setChains(List<ChainAddress> chains) {
        this.chains = chains;
    }


    public static class ChainAddress {
        private String chain;
        private String address;

        public ChainAddress() {
        }

        public String getChain() {
            return this.chain;
        }

        public String getAddress() {
            return this.address;
        }

        public void setChain(String chain) {
            this.chain = chain;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}