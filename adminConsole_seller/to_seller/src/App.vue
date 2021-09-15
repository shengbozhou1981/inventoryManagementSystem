<template>
    <div id="app">
        <router-view/>
        <audio hidden id="notice" :src="music"></audio>
    </div>
</template>

<script>
    import HelloWorld from './components/HelloWorld.vue'

    export default {
        name: 'app',
        components: {
            HelloWorld
        },

        data() {
            return {
                websock: null,
                music: 'alert.mp3',
            }
        },

        methods: {
            initWebSocket() {
                this.websock = new WebSocket('ws://localhost:8085/webSocket');
                this.websock.onmessage = this.webSocketOnMessage;
                this.websock.onopen = this.webSocketOnOpen;
                this.websock.onerror = this.webSocketOnerror;
                this.websock.onclose = this.webSocketClose;
            },
            webSocketOnOpen(event) {
                console.log('create connection')
            },
            webSocketOnMessage(event) {
                alert("Test")
                console.log("receive message from backend")
                document.getElementById('notice').play();
                const _this = this
                this.$alert('new order', 'message', {
                    confirmButtonText: 'Confirm',
                    callback: action => {
                        _this.$router.push('/orderManage')
                    }
                });
            },
            webSocketClose(event) {
                console.log('Connection closed');
            },
            webSocketOnerror(event){
              console.log("error occurs")
            }
        },
        created() {
            this.initWebSocket();
        },
        destroyed() {
            this.websock.close()
        }
    }
</script>

<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        margin-top: 60px;
    }
</style>
