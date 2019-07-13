import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGoods } from 'app/shared/model/goods.model';
import { GoodsService } from './goods.service';

@Component({
  selector: 'jhi-goods-delete-dialog',
  templateUrl: './goods-delete-dialog.component.html'
})
export class GoodsDeleteDialogComponent {
  goods: IGoods;

  constructor(protected goodsService: GoodsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.goodsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'goodsListModification',
        content: 'Deleted an goods'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-goods-delete-popup',
  template: ''
})
export class GoodsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ goods }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(GoodsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.goods = goods;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/goods', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/goods', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
