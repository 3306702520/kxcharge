<template>
	<s-layout navbar="normal" onShareAppMessage>
		<view class="block">
			<view class="carInfo">
				<image src="https://kxcharge.oss-cn-beijing.aliyuncs.com/add-car-image.png" mode="aspectFit"></image>
				<view class="carType">
					<text class="title">车型</text>
					<text>新能源汽车</text>
				</view>
				<view class="plateNum" @click="inputPlate">
					<text class="title">车牌号</text>
					<view class="num">
						<text class="numItem" v-for="(item,index) in blockArr" :key="index">{{item || ''}}</text>
					</view>
				</view>
				<view class="isDefault" v-if="!isEdit">
					<text class="title">是否为默认充电车辆</text>
					<uv-switch v-model="defaultCar"></uv-switch>
				</view>
				<view class="carType" v-else>
					<text class="title">车辆识别代码</text>
					<text>未认证</text>
				</view>
			</view>
			<uv-button type="primary" text="添加车辆" shape="circle" @click="addCar('add')" v-if="!isEdit"></uv-button>
			<view v-else>
				<uv-button type="primary" text="编辑车辆" shape="circle" @click="addCar('edit')"></uv-button>
				<view class="delCar" @click="onDelCar">删除车辆</view>
			</view>
			<!-- <text @click="goBack" class="goBack">我再想想</text> -->
			<uv-popup ref="popup" mode="bottom" @change="onPopupChange">
				<view class="popup-content">
					<view class="btn">
						<text @click="()=>$refs.popup.close()">取消</text>
						<text @click="submitPlate">确定</text>
					</view>
					<view>
						<text class="label">车牌号</text>
						<view class="num">
							<text class="text-block" v-for="(item,index) in popBlockArr" :key="index">{{item || ''}}</text>
						</view>
					</view>
					<uv-keyboard :autoChange="true" ref="uKeyboard" mode="car" :overlay="false" @change="clickKeyboard($event)"
						:tooltip="false" @backspace="backspace"></uv-keyboard>
				</view>
			</uv-popup>

			<uv-modal ref="modal" content='是否确认删除该车辆?' title='提示' :showCancelButton="true" class="modal"
				@cancel="showModal = false" @confirm="submitDel"></uv-modal>
		</view>
	</s-layout>
</template>

<script>
	import {
		delCar,
		createCar,
		editCar
	} from "@/sheep/api/charge/member"
	export default {
		data() {
			return {
				defaultCar: true,
				showModal: false,
				plateValue: '',
				isFocus: false,
				time1: 0,
				time2: 0,
				time3: 0,
				blockArr: new Array(8),
				popBlockArr: new Array(8),
				isEdit: false,
				carId: '',
			};
		},
		methods: {
			clickKeyboard(event) {
				if (this.plateValue.length >= 8) {
					return;
				}
				this.plateValue += event;
				this.plateValue.split("").forEach((k, index) => {
					this.popBlockArr[index] = k;
				});
			},
			backspace() {
				this.popBlockArr = this.popBlockArr.map((item, index, arr) => {
					if (!arr[index + 1]) item = '';
					return item;
				});
				this.plateValue = this.popBlockArr.join("")
				// console.log('this.popBlockArr: ',this.popBlockArr, this.plateValue);			
			},
			addCar(type) {
				if (!this.plateValue) {
					uni.showToast({
						icon: 'none',
						title: '请输入车牌号'
					})
					return;
				}
				const data = {
					plateNumber: this.plateValue,
					isDefault: Number(this.defaultCar),
					plateType: 1
				}
				if (this.carId) {
					data['id'] = this.carId;
				}
				try {
					const method = type == "add" ? createCar : editCar;
					const tip = type == "add" ? '添加' : '编辑'
					method(data).then(res => {
						console.log('res: ', res);
						uni.showToast({
							title: `${tip}成功!`,
							icon: 'success'
						})
						// 使用 setTimeout 延迟跳转
						setTimeout(() => {
							uni.navigateTo({
								url: `/pages/my-car/my-car`
							});
						}, 1500); // 2000 毫秒 = 2 秒
					}).catch(err => {
						uni.showToast({
							title: `${tip}失败!`,
							icon: 'error'
						})
					})
				} catch (e) {
					uni.showToast({
						title: '添加失败!',
						icon: 'error'
					})
				}
			},

			goBack() {
				uni.navigateBack();
			},
			inputPlate() {
				// if (this.isEdit) return;
				this.$refs.popup.open()
				this.$refs.uKeyboard.open()
			},
			onPopupChange(e) {
				if (e) {
					this.open()
				} else {
					this.close()
				}
			},
			open() {
				this.$refs.popup.open();
				this.time2 = setTimeout(() => {
					this.isFocus = true;
				}, 1000)
			},
			close() {
				this.isFocus = false;
				this.plateValue = '';
			},
			submitPlate() {
				if (!this.plateValue) {
					uni.showToast({
						icon: 'none',
						title: '输入的车牌号是否为空'
					})
					return;
				}
				// const plateArr = this.plateValue.split("")
				if (this.plateValue.length !== 8 && this.plateValue.length !== 7) {
					uni.showToast({
						icon: 'none',
						title: '输入的车牌号有误，请重新输入'
					})
					return;
				}
				this.$refs.popup.close()
				this.$refs.uKeyboard.close()

				this.plateValue.split("").forEach((k, index) => {
					this.blockArr[index] = k;
				});
				this.blockArr = this.blockArr.filter(k => k);
			},
			onDelCar() {
				this.$refs.modal.open();
			},
			submitDel() {
				try {
					delCar(this.carId).then(res => {
						this.$refs.modal.close();;
						uni.showToast({
							title: '删除成功!',
							icon: 'success'
						})
						// 使用 setTimeout 延迟跳转
						setTimeout(() => {
							uni.navigateTo({
								url: `/pages/my-car/my-car`
							});
						}, 1500); // 2000 毫秒 = 2 秒
					}).catch(err => {
						console.log('err: ', err);
						uni.showToast({
							title: '删除失败!',
							icon: 'error'
						})
					})
				} catch (e) {
					//TODO handle the exception
					uni.showToast({
						title: '删除失败!',
						icon: 'error'
					})
				}

			}
		},
		beforeDestroy() {
			clearTimeout(this.time1);
			clearTimeout(this.time2);
			clearTimeout(this.time3);
		},
		onLoad(options) {
			if (options.carInfo) {
				console.log('options.carInfo: ', options.carInfo);
				const info = JSON.parse(options.carInfo);
				this.defaultCar = info.isDefault
				this.plateValue = info.plateNumber;
				this.plateValue.split("").forEach((k, index) => {
					this.blockArr[index] = k;
				});
				this.blockArr = this.blockArr.filter(k => k);
				this.popBlockArr = this.blockArr;
				this.isEdit = true;
				this.carId = info.id;
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {

		background-color: #f0f3f8;
	}

	.block {
		height: 100vh;
		padding: 15rpx;

		.carInfo {
			background-color: #fff;
			border-radius: 20rpx;

			image {
				width: 730rpx;
				height: 365rpx;
			}

			.carType {
				padding: 0 30rpx 30rpx;
				display: flex;
				justify-content: space-between;
				font-size: 35rpx;
			}

			.title {
				color: #999999;
				margin-right: 25rpx;
			}

			.plateNum {
				display: flex;
				align-items: center;
				justify-content: space-between;
				padding: 0 15rpx 30rpx;

				.num {
					display: flex;
				}

				.numItem {
					display: inline-block;
					margin-right: 10rpx;
					width: 60rpx;
					background-color: #f2f2f2;
					height: 70rpx;
					line-height: 50rpx;
					text-align: center;
					padding: 13rpx;
				}
			}

			.isDefault {
				padding: 0 25rpx 30rpx 15rpx;
				display: flex;
				justify-content: space-between;
				align-items: center;
			}
		}

		.delCar {
			margin-top: 25rpx;
			display: flex;
			justify-content: center;
			align-items: center;
			color: #797979;
			font-size: 30rpx;
		}

		.uv-button {
			margin-top: 40rpx;
			width: 720rpx !important;
			height: 110rpx !important;

			.uv-button__text {
				font-size: 35rpx !important;
			}
		}

		.goBack {
			display: flex;
			justify-content: center;
			align-items: center;
			color: #797979;
			font-size: 40rpx;
			margin: 30rpx auto;
		}

		.popup-content {
			padding: 35rpx 35rpx 450rpx;
			position: relative;

			.label {
				font-size: 22rpx;
				color: #797979;
			}

			.btn {
				color: #1890ff;
				display: flex;
				justify-content: space-between;
				align-items: center;
				margin-bottom: 50rpx;
			}

			.num {
				display: flex;
				justify-content: space-around;
				align-items: center;
			}

			.text-block {
				display: inline-block;
				margin-right: 10rpx;
				width: 60rpx;
				background-color: #f2f2f2;
				height: 70rpx;
				line-height: 80rpx;
				text-align: center;
			}

			.uv-popup {
				position: absolute;
				bottom: 0;

				.uv-popup__content {
					padding: 0;
				}
			}
		}
	}
</style>