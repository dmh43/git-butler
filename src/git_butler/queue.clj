(ns git-butler.queue)

(defn drop-branch
  [shipping-queue branch-name]
  (remove (partial = branch-name) shipping-queue))

(defn push-branch
  [shipping-queue branch-name]
  (if (not-any? (partial = branch-name) shipping-queue)
    (conj shipping-queue branch-name)
    shipping-queue))

(defn parent-branch
  [shipping-queue feature-branch-name base-branch-name]
  (if (= 1 (count shipping-queue))
    base-branch-name
    (reduce
     (fn [parent next-branch-name]
       (if (= next-branch-name feature-branch-name)
         (reduced parent)
         next-branch-name))
     shipping-queue)))
