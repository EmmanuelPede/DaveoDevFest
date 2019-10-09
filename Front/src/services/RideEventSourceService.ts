import Vue from "vue";

export const RideEventSourceService = new Vue({
    data() {
        return {
            eventSource: <EventSource> null
        }
    },
    methods: {
        init() {
            if (!this.eventSource) {
                this.eventSource = new EventSource('http://localhost:8080/api/score');

                this.eventSource.addEventListener('second', e => {
                    console.log('second', e);
                }, false);

                this.onMessage();

                this.onOpen();

                this.onError();
            }

            console.log('creation event source', this.eventSource);
            return this.eventSource;
        },

        onMessage() {
            return new Promise((resolve, reject) => {
                if (this.eventSource) {
                    this.eventSource.onmessage = e => {
                        this.$emit('lastRide', e.data);
                        resolve(e.data);
                    };
                } else {
                    reject('EventSource not init')
                }
            });
        },

        onError(): Promise<any> {
            return new Promise(((resolve, reject) => {
                if (this.eventSource) {
                    this.eventSource.onerror = e => {
                        if (this.eventSource.readyState == EventSource.CLOSED) {
                            console.log('close');
                        } else {
                            console.log(e);
                        }
                        resolve(e);
                    }
                } else {
                    reject('EventSource not init')
                }
            }));
        },

        onOpen(): Promise<any> {
            return new Promise(((resolve, reject) => {
                if (this.eventSource) {
                    this.eventSource.onopen = e => {
                        console.log('open');
                        resolve(e);
                    }
                } else {
                    reject('EventSource not init')
                }
            }));
        },

        close() {
            if (this.eventSource) {
                this.eventSource.close();
            }
        }
    },

});
