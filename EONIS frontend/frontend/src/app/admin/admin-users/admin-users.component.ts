import { Component, OnInit } from '@angular/core';
import { UserService } from '../../users/user.service';
import { User } from '../../users/user';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.css']
})
export class AdminUsersComponent implements OnInit {
  users: User[] = [];

  // paging + sort
  totalElements = 0;
  page = 0;
  pageSize = 10;
  sort = 'id,desc';

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getAllUsers(this.page, this.pageSize, this.sort).subscribe({
      next: (res) => {
        this.users = res.content;
        this.totalElements = res.totalElements;
      },
      error: (err) => console.error('Greška pri učitavanju korisnika', err)
    });
  }

  deleteUser(id: number): void {
    if (confirm('Da li ste sigurni da želite da obrišete ovog korisnika?')) {
      this.userService.deleteUser(id).subscribe({
        next: () => this.loadUsers(),
        error: (err) => console.error('Greška pri brisanju korisnika', err)
      });
    }
  }

  changePage(event: PageEvent) {
    this.page = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadUsers();
  }

  setSort(field: string) {
    const [currField, currDir] = this.sort.split(',');
    const dir = (currField === field && currDir === 'asc') ? 'desc' : 'asc';
    this.sort = `${field},${dir}`;
    this.loadUsers();
  }
}
