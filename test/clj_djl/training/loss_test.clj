(ns clj-djl.training.loss-test
  (:require [clojure.test :refer :all]
            [clj-djl.ndarray :as nd]
            [clj-djl.training.loss :as loss]))

(deftest loss
  (testing "l1-loss test."
    (with-test
      (def manager (nd/new-base-manager))
      (def pred  (nd/create manager (float-array [1 2 3 4 5])))
      (def label (nd/ones manager [5]))
      ;; L1 = \sum |pred_i - label_i| = 0+1+2+3+4 = 10; 10/5 = 2
      (is (> 1e-4 (Math/abs (- 2.0 (-> (loss/l1-loss) (loss/evaluate label pred) (nd/get-element)))))))))

(deftest l2-loss
  (testing "l2-loss test."
    (with-test
      (def manager (nd/new-base-manager))
      (def pred  (nd/create manager (float-array [1 2 3 4 5])))
      (def label (nd/ones manager [5]))
      ;; L2 = 0.5 * \sum |label_i - pred_i|^2 = 1/2 * (1+4+9+16) = 15; 15/5 = 3
      (is (> 1e-4 (Math/abs (- 3. (-> (loss/l2-loss) (loss/evaluate label pred) (nd/get-element)))))))))

(deftest hinge-loss
  (testing "hinge loss test"
    (with-test
      (def manager (nd/new-base-manager))
      (def pred  (nd/create manager (float-array [1 2 3 4 5])))
      (def label (nd/create manager (int-array [1 1 -1 -1 1])))
      ;; L = \sum {max(0, 0), max(0, -1), max(0 4), max(0, 5), max(0, -4)} = 9; 9/5 = 1.8
      (is (> 1e-4 (Math/abs (- 1.8 (-> (loss/hinge-loss) (loss/evaluate label pred) (nd/get-element)))))))))