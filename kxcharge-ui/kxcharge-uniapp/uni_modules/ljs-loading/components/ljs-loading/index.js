export default {
	// emits: ['input', 'update:value'],
	props: {
		// 转速，单位秒
		speed: {
			type: Number,
			default: 3
		},
		// 高斯模糊
		gaussian: {
			type: Number,
			default: 1
		},
		// 背景透明度，0~1的数字。
		bgOpacity: {
			type: Number,
			default: 0.5
		},
		// solt字体颜色
		textColor: {
			type: String,
			default: '#FFF'
		},
	},
	data() {
		return {
		}
	},
	mounted() {
		this.init();
	},
	methods: {
		init(){
			// #ifdef H5
			// document.getElementById('ljs-loading').style.setProperty('--time', this.speed);
			// #endif
		}
	}
}