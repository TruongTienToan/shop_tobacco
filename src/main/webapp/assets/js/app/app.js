class App {

    static ERROR_400 = "Giao dịch không thành công, vui lòng kiểm tra lại dữ liệu.";
    static ERROR_401 = "Access Denied! Invalid credentials.";
    static ERROR_403 = "Access Denied! You are not authorized to perform this function.";
    static ERROR_404 = "An error occurred. Please try again later!";
    static ERROR_500 = "Lưu dữ liệu không thành công, vui lòng liên hệ quản trị hệ thống.";

    static SweetAlert = class {
        static showSuspendConfirmDialog() {
            return Swal.fire({
                icon: 'warning',
                text: 'Bạn có chắc muốn xoá sản phẩm này không?',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Đồng ý!',
                cancelButtonText: 'Huỷ',
            })
        }

        static showSuspendConfirmDialogCustomer() {
            return Swal.fire({
                icon: 'warning',
                text: 'Bạn có chắc muốn khoá tài khoản này không?',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Đồng ý!',
                cancelButtonText: 'Huỷ',
            })
        }

        static showUnlockConfirmDialogCustomer() {
            return Swal.fire({
                icon: 'success',
                text: 'Bạn có chắc muốn mở khoá tài khoản này không?',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Đồng ý!',
                cancelButtonText: 'Huỷ',
            })
        }

        static showSuccessAlert(t) {
            Swal.fire({
                icon: 'success',
                title: t,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1500
            })
        }

        static showErrorAlert(t) {
            Swal.fire({
                icon: 'error',
                title: 'Warning',
                text: t,
            })
        }
    }

    static IziToast = class {
        static showSuccessAlert(t) {
            iziToast.success({
                title: 'OK',
                position: 'topRight',
                timeout: 1500,
                message: t
            });
        }

        static showErrorAlert(t) {
            iziToast.error({
                title: 'Error',
                position: 'topRight',
                timeout: 1500,
                message: t
            });
        }
    }
}
class Customer {
    constructor(id, fullName, email, phone, address, isDeleted) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.isDeleted = isDeleted;
    }
}

class User{
    constructor(id, username, password, phone, address, role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }
}
class Product {
    constructor(id, name, image, amount, price, isDeleted) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.amount = amount;
        this.price = price;
        // this.category = category;
        this.isDeleted = isDeleted;
    }
}

class CartItem {
    constructor(id,productId, title,image, price, quantity, amount) {
        this.id = id;
        this.productId = productId;
        this.title = title;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
    }
}

class Status {
    constructor(id, status) {
        this.id = id;
        this.statusName = status;
    }
}


class Category {
    constructor(id, category) {
        this.id = id;
        this.categoryName = category;
    }
}
