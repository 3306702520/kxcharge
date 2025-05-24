import request from '@/config/axios'

export const createVendingMachine = async (data) => {
  return await request.post({ url: '/sell/vending-machine/create', data })
}

export const getVendingMachine =async (params) => {
  return await request.get({ url: '/sell/vending-machine/page', params })
}

export const getVendingMachineInfo = async (id) => {
  return await request.get({ url: `/sell/vending-machine/get?id=` + id })
} 

export const updateVendingMachine = async (data) => {
  return await request.put({ url: '/sell/vending-machine/update', data })
}

export const updateVendingMachineStatus = async (id, status) => {
  return await request.put({ url: `/sell/vending-machine/enable/${id}/${status}` })
}

export const delVendingMachine = async (id) => {
  return await request.delete({ url: `/sell/vending-machine/delete?id=${id}` })
}

export const updateSlotType = async (data) => {
  return await request.put({ url: '/sell/vending-machine/update/slotType', data })
}

export const getVendingMachineList = async () => {
  return await request.get({ url: 'sell/vending-machine/list' })
}

export const getSlots = async (id) => {
  return await request.get({ url: `/sell/vending-machine/getSlots?id=${id}` })
}

