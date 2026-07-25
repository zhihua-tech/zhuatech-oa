/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
import http from './http'
export const api = {
  login: data => http.post('/auth/login', data), me: () => http.get('/auth/me'), dashboard: () => http.get('/dashboard'),
  notices: () => http.get('/notices'), attendanceToday: () => http.get('/attendance/today'), attendanceHistory: () => http.get('/attendance'), checkIn: () => http.post('/attendance/check-in'), checkOut: () => http.post('/attendance/check-out'),
  leaves: () => http.get('/leaves'), pendingLeaves: () => http.get('/leaves/pending'), createLeave: data => http.post('/leaves', data), approveLeave: (id,data) => http.post(`/leaves/${id}/approve`, data),
  tasks: () => http.get('/tasks'), createTask: data => http.post('/tasks', data), setTask: (id,completed) => http.patch(`/tasks/${id}`, { completed }), deleteTask: id => http.delete(`/tasks/${id}`),
  departments: () => http.get('/organization/departments'), contacts: () => http.get('/organization/contacts')
}
